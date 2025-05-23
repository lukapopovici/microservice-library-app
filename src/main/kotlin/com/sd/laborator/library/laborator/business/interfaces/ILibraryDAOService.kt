package com.sd.laborator.library.laborator.business.interfaces

import com.sd.laborator.library.laborator.business.models.Book

interface ILibraryDAOService {
    fun getBooks(): Set<Book>
    fun addBook(book: Book)
    fun findAllByAuthor(author: String): Set<Book>
    fun findAllByTitle(title: String): Set<Book>
    fun findAllByPublisher(publisher: String): Set<Book>
}