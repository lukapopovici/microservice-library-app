package com.sd.laborator.library.laborator.business.interfaces

import com.sd.laborator.library.laborator.business.models.Book

interface IHTMLPrinter {
    fun printHTML(books: Set<Book>): String
}