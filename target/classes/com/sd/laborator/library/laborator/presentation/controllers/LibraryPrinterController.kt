package com.sd.laborator.library.laborator.presentation.controllers

import com.sd.laborator.library.laborator.business.services.CacheService
import com.sd.laborator.library.laborator.business.services.LibraryDAOService
import com.sd.laborator.library.laborator.business.services.LibraryPrinterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LibraryPrinterController {
    @Autowired
    private lateinit var libraryDAOService: LibraryDAOService

    @Autowired
    private lateinit var libraryPrinterService: LibraryPrinterService

    @Autowired
    private lateinit var cacheService: CacheService

    @GetMapping("/print")
    fun printBooks(@RequestParam format: String): String {
        val books = libraryDAOService.getBooks()
        return when (format.lowercase()) {
            "html" -> libraryPrinterService.printHTML(books)
            "json" -> libraryPrinterService.printJSON(books)
            else -> libraryPrinterService.printRaw(books)
        }
    }

    @GetMapping("/find")
    fun findBooks(
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) publisher: String?,
        @RequestParam(defaultValue = "raw") format: String
    ): String {
        val query = mutableMapOf<String, String>()
        author?.let { query["author"] = it }
        title?.let { query["title"] = it }
        publisher?.let { query["publisher"] = it }

        return cacheService.getResult(query, format)
    }
}