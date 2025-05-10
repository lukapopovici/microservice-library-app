
package com.sd.laborator.library.laborator.business.services

import com.sd.laborator.library.laborator.business.repository.CacheRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class CacheCleanupService(private val cacheRepository: CacheRepository) {

    @Scheduled(fixedRate = 3600000)
    @Transactional
    fun cleanupOldEntries() {
        val oneHourAgo = Instant.now().epochSecond - 3600
        cacheRepository.deleteByTimestampLessThan(oneHourAgo)
    }
}