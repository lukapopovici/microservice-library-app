package com.sd.laborator.library.laborator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class LibraryApp

fun main(args: Array<String>) {
    runApplication<LibraryApp>(*args)
}
