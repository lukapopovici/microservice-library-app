package com.sd.laborator.library.laborator.business.interfaces

import com.sd.laborator.library.laborator.business.models.Book

interface IRawPrinter {
    fun printRaw(books: Set<Book>): String
}