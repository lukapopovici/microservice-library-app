package com.sd.laborator.library.laborator.business.services

import com.sd.laborator.library.laborator.business.models.Book
import com.sd.laborator.library.laborator.business.repository.BookRepository
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
class LibraryDAOService {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @PostConstruct
    fun initializeData() {
        if (bookRepository.count() == 0L) {
            val initialBooks = listOf(
                Book(
                    title = "Harry Potter",
                    author = "J.K. Rowling",
                    publisher = "Bloomsbury",
                    text = "Magic story"
                ),
                Book(
                    title = "1984",
                    author = "George Orwell",
                    publisher = "Secker and Warburg",
                    text = "Dystopian fiction"
                )
            )
            bookRepository.saveAll(initialBooks)
        }
    }

    fun getBooks(): Set<Book> = bookRepository.findAll().toSet()

    fun findByAuthor(author: String): Set<Book> = bookRepository.findByAuthorIgnoreCase(author)

    fun findByName(title: String): Set<Book> = bookRepository.findByTitleIgnoreCase(title)

    fun findByPublisher(publisher: String): Set<Book> = bookRepository.findByPublisherIgnoreCase(publisher)
}