package com.example.kafkastreamexample.stream

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

data class TestMessage(
    val message: String
)

private val log = KotlinLogging.logger {}

@Configuration
class KafkaSampleStream {

    @Bean
    fun testMessage() = Consumer<TestMessage> {
        log.info { "work kafka stream $it" }
    }

    @Bean
    fun basicConsumer() = Consumer<String> {
        // log.info { "basicConsumer Work ==> $it" }
    }

    @Bean
    fun basicConsumer2() = Consumer<String> {
        log.info { "basicConsumer2 Work ==> $it" }
        error(it)
    }

    private fun error(it: String) {
        val array = it.split("-")
        val num = array[2].toInt()
        if (num % 2 == 0) {
            log.warn { "BackOffException Recode:${it} And Num:${num}" }
            throw RuntimeException("Test Back-Off Ok")
        }
    }
}
