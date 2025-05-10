package com.sd.laborator.library.laborator.presentation.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    companion object {
        const val PRINTER_FILE_QUEUE = "printer.file"
        const val PRINTER_COMMAND_QUEUE = "printer.command"
        const val PRINTER_STATUS_QUEUE = "printer.status"
    }

    @Bean
    fun printerFileQueue() = Queue(PRINTER_FILE_QUEUE, false)

    @Bean
    fun printerCommandQueue() = Queue(PRINTER_COMMAND_QUEUE, false)

    @Bean
    fun printerStatusQueue() = Queue(PRINTER_STATUS_QUEUE, false)

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory) = RabbitTemplate(connectionFactory)
}