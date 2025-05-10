package com.sd.laborator.library.laborator.business.models

data class PrinterMessage(
    val type: MessageType,
    val content: String,
    val format: String? = null
)

enum class MessageType {
    FILE, COMMAND, STATUS
}