package com.example.kafkastreampushsample.scheduler

import com.example.kafkastreampushsample.domain.repository.OutboxRepository
import com.example.kafkastreampushsample.producer.BasicProducerClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class KafkaAutoPushScheduler(
    private val basicProducerClient: BasicProducerClient,
    private val outboxRepository: OutboxRepository
) {

    // 1 초 마다 실행을 한다.
    @Scheduled(cron = "0/1 * * * * ?")
    fun sendMessage() {
        log.info { "Work Send Scheduler" }
        basicProducerClient.send()
    }

    // 1 초 마다 실행을 한다.
    @Scheduled(cron = "0/1 * * * * ?")
    fun senOutBox() {
        // 미발송된 outbox 메세지를 리스트로 추출
        val findOutboxMessageNotSendList = outboxRepository.findAll()

        findOutboxMessageNotSendList.forEach { m ->
            {

                val payload = m.payload

                // 추출된 payload 를 카프카 메세지를 발생하도록 구현
                basicProducerClient.send(payload)

                // 발송 상태를 변경
                m.confirm()
            }
        }
    }
}
