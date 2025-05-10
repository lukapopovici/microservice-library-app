package com.sd.laborator.library.laborator.business.repository

import com.sd.laborator.library.laborator.business.models.CacheEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CacheRepository : JpaRepository<CacheEntry, Long> {
    @Query("SELECT c FROM CacheEntry c WHERE c.query = ?1 ORDER BY c.timestamp DESC LIMIT 1")
    fun findByQuery(query: String): CacheEntry?

    fun findByQueryAndTimestampGreaterThan(query: String, timestamp: Long): List<CacheEntry>

    fun deleteByTimestampLessThan(timestamp: Long)
}