package com.sd.laborator.library.laborator.business.models

import jakarta.persistence.*

@Entity
@Table(name = "books",
    uniqueConstraints = [UniqueConstraint(columnNames = ["author", "title", "publisher"])])
data class Book(
    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var author: String,

    @Column(nullable = false)
    var publisher: String,

    @Column(columnDefinition = "TEXT")
    var text: String,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
) {
    constructor() : this("", "", "", "", 0)
}