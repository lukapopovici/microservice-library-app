package com.sd.laborator.library.laborator.business.services

import com.sd.laborator.library.laborator.business.interfaces.ILibraryPrinterService
import com.sd.laborator.library.laborator.business.models.Book
import org.springframework.stereotype.Service

@Service
class LibraryPrinterService : ILibraryPrinterService {
    override fun printHTML(books: Set<Book>): String {
        var content: String = "<html><head><title>Libraria mea HTML</title></head><body>"
        books.forEach {
            content += "<p><h3>${it.title}</h3><h4>${it.author}</h4><h5>${it.publisher}</h5>${it.text}</p><br/>"
        }
        content += "</body></html>"
        return content
    }

    override fun printJSON(books: Set<Book>): String {
        var content: String = "[\n"
        books.forEach {
            if (it != books.last())
                content += "    {\"Titlu\": \"${it.title}\", \"Autor\":\"${it.author}\", \"Editura\":\"${it.publisher}\", \"Text\":\"${it.text}\"},\n"
            else
                content += "    {\"Titlu\": \"${it.title}\", \"Autor\":\"${it.author}\", \"Editura\":\"${it.publisher}\", \"Text\":\"${it.text}\"}\n"
        }
        content += "]\n"
        return content
    }

    override fun printRaw(books: Set<Book>): String {
        var content: String = ""
        books.forEach {
            content += "${it.title}\n${it.author}\n${it.publisher}\n${it.text}\n\n"
        }
        return content
    }
}