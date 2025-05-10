package com.sd.laborator.library.laborator.business.interfaces

import com.sd.laborator.library.laborator.business.models.Book

interface IJSONPrinter {
    fun printJSON(books: Set<Book>): String
}