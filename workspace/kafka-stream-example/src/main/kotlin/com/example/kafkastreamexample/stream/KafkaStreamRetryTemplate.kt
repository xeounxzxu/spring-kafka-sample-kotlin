package com.example.kafkastreamexample.stream

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.cloud.stream.annotation.StreamRetryTemplate
import org.springframework.context.annotation.Bean
import org.springframework.retry.RetryCallback
import org.springframework.retry.RetryContext
import org.springframework.retry.RetryListener
import org.springframework.retry.support.RetryTemplate

private val log = KotlinLogging.logger {}

//@Configuration
class KafkaStreamRetryTemplate {

    @Bean
    @StreamRetryTemplate
    fun basicConsumer2RetryTemplate(): RetryTemplate =
        RetryTemplate.builder()
            .maxAttempts(4)
            .exponentialBackoff(5000, 2.0, 30000)
            .withListener(listener)
            .build()

    // 에러 발생에 대한 리스너를 구현합니다.
    private val listener = object : RetryListener {
        override fun <T, E : Throwable> onError(
            retryContext: RetryContext,
            retryCallback: RetryCallback<T, E>,
            throwable: Throwable
        ) {
            log.info("=========== RETRY ERROR ${retryContext.retryCount} 번째 발생 ================")
            log.info("Retry Exception Trace::: ", throwable.cause)
        }
    }
}
