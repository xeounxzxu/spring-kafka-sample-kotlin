package com.example.kafkastreampushsample.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class BasicProducerClient(
    private val streamBridge: StreamBridge
) {

    private val testGroup = "TEST-202"

    private var count = 0

    fun send(payload: String? = null) {
        log.info { "work send topic - start" }
        val message = "$testGroup-${count++}"
        streamBridge.send(
            "basicProducer-out-0",
            message
        )
        log.info { "Send Message $message" }
        log.info { "work send topic - end" }
    }
}
