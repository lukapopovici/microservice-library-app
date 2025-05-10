package com.sd.laborator.library.laborator.business.services

import com.sd.laborator.library.laborator.business.merkle.MerkleTree
import com.sd.laborator.library.laborator.business.repository.CacheRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class MerkleTreeService(private val cacheRepository: CacheRepository) {
    private val logger = LoggerFactory.getLogger(MerkleTreeService::class.java)
    private var merkleTree = MerkleTree()

    @Scheduled(fixedRate = 60000)
    fun rebuildTree() {
        val cacheEntries = cacheRepository.findAll()
        val data = if (cacheEntries.isEmpty()) {
            logger.info("Cache is empty, building empty Merkle tree")
            listOf("empty")
        } else {
            cacheEntries.map { "${it.query}:${it.result}" }
        }
        merkleTree.buildTree(data)
        logger.info("Merkle tree rebuilt with ${data.size} entries")
    }

    fun searchInCache(query: String): Boolean {
        return merkleTree.search(query)
    }
}