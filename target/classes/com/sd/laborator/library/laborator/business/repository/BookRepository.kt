package com.sd.laborator.library.laborator.business.repository

import com.sd.laborator.library.laborator.business.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByAuthorIgnoreCase(author: String): Set<com.sd.laborator.library.laborator.business.models.Book>
    fun findByTitleIgnoreCase(title: String): Set<com.sd.laborator.library.laborator.business.models.Book>
    fun findByPublisherIgnoreCase(publisher: String): Set<com.sd.laborator.library.laborator.business.models.Book>
}

