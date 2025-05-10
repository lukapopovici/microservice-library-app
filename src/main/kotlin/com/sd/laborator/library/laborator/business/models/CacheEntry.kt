package com.sd.laborator.library.laborator.business.models

import jakarta.persistence.*
import java.time.Instant
import java.time.temporal.ChronoUnit

@Entity
@Table(name = "cache",
    uniqueConstraints = [UniqueConstraint(columnNames = ["timestamp", "query", "result"])])
data class CacheEntry(
    @Column(nullable = false)
    var query: String,

    @Column(nullable = false)
    var result: String,

    @Column(nullable = false)
    var timestamp: Long = Instant.now().epochSecond,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
) {
    constructor() : this("", "", Instant.now().epochSecond)

    fun isValid(): Boolean {
        val now = Instant.now().epochSecond
        return (now - timestamp) < 3600
    }
}