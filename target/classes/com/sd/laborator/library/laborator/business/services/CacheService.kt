package com.sd.laborator.library.laborator.business.services

import com.sd.laborator.library.laborator.business.models.CacheEntry
import com.sd.laborator.library.laborator.business.models.PrinterMessage
import com.sd.laborator.library.laborator.business.models.MessageType
import com.sd.laborator.library.laborator.business.repository.CacheRepository
import com.sd.laborator.library.laborator.presentation.config.RabbitMQConfig.Companion.PRINTER_FILE_QUEUE
import com.sd.laborator.library.laborator.presentation.config.RabbitMQConfig.Companion.PRINTER_STATUS_QUEUE
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CacheService(
    private val cacheRepository: CacheRepository,
    private val libraryDAOService: LibraryDAOService,
    private val libraryPrinterService: LibraryPrinterService,
    private val merkleTreeService: MerkleTreeService,
    private val rabbitTemplate: RabbitTemplate
) {
    private val logger = LoggerFactory.getLogger(CacheService::class.java)

    fun getResult(query: Map<String, String>, format: String): String {
        val queryString = query.entries.joinToString(";") { "${it.key}=${it.value}" }

        if (!merkleTreeService.searchInCache(queryString)) {
            val cachedEntry = cacheRepository.findByQuery(queryString)
            if (cachedEntry != null && cachedEntry.isValid()) {
                logger.info("Normal cache HIT after Merkle miss for query: $queryString")
                sendFile(cachedEntry.result, format)
                sendStatus("CACHE_HIT")
                return cachedEntry.result
            }
            logger.info("Complete cache MISS for query: $queryString")
            sendStatus("CACHE_MISS")
            return performSearch(queryString, query, format)
        }

        val cachedEntry = cacheRepository.findByQuery(queryString)
        if (cachedEntry != null && cachedEntry.isValid()) {
            logger.info("Cache HIT verified for query: $queryString")
            sendFile(cachedEntry.result, format)
            sendStatus("CACHE_HIT")
            return cachedEntry.result
        }

        logger.info("Cache MISS despite Merkle hit for query: $queryString")
        sendStatus("CACHE_MISS")
        return performSearch(queryString, query, format)
    }

    private fun performSearch(queryString: String, query: Map<String, String>, format: String): String {
        val books = when {
            query["author"] != null -> libraryDAOService.findByAuthor(query["author"]!!)
            query["title"] != null -> libraryDAOService.findByName(query["title"]!!)
            query["publisher"] != null -> libraryDAOService.findByPublisher(query["publisher"]!!)
            else -> libraryDAOService.getBooks()
        }

        val result = when (format.lowercase()) {
            "html" -> libraryPrinterService.printHTML(books)
            "json" -> libraryPrinterService.printJSON(books)
            else -> libraryPrinterService.printRaw(books)
        }

        sendFile(result, format)

        val cacheEntry = CacheEntry(
            query = queryString,
            result = result,
            timestamp = Instant.now().epochSecond
        )
        cacheRepository.save(cacheEntry)
        logger.info("New result cached for query: $queryString")

        return result
    }

    private fun sendFile(content: String, format: String) {
        val message = PrinterMessage(MessageType.FILE, content, format)
        rabbitTemplate.convertAndSend(PRINTER_FILE_QUEUE, message)
    }

    private fun sendStatus(status: String) {
        val message = PrinterMessage(MessageType.STATUS, status)
        rabbitTemplate.convertAndSend(PRINTER_STATUS_QUEUE, message)
    }
}