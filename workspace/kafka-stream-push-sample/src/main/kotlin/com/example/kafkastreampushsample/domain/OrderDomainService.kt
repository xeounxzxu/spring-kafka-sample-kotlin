package com.example.kafkastreampushsample.domain

import com.example.kafkastreampushsample.domain.model.Order
import com.example.kafkastreampushsample.domain.repository.OrderEntity
import com.example.kafkastreampushsample.domain.repository.OrderRepository
import com.example.kafkastreampushsample.domain.repository.OutboxEntity
import com.example.kafkastreampushsample.domain.repository.OutboxRepository
import com.example.kafkastreampushsample.producer.BasicProducerClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class OrderDomainService(
    private val orderRepository: OrderRepository,
    private val outboxRepository: OutboxRepository,
    private val orderEventClient: BasicProducerClient,
) {

    @Transactional(
        // 독립적인 트랜잭션 레벨 실행
        propagation = Propagation.REQUIRES_NEW,
    )
    // 주문의 마지막 단계 컴펌 처리를 위한 생성 메소드
    fun order(
        order: Order
    ): OrderEntity {
        return outbox(
            payload = order.toPayload(),
            aggregateId = ORDER_CREATED_ID
        ) {
            // 주문을한다.
            val entity = orderRepository.save(order.toEntity())

            // 주문을 했어라는 주문 이벤트를 발행을 한다.
            // orderEventClient.send(payload)

            // 타 패키지의 저장된 주문 정보를 넘기기 위한 return
            entity
        }
    }

    // 비지니스 로직 과 계위를 나누기 위한 함수화
    fun <T> outbox(
        payload: String,
        aggregateId: String,
        action: () -> T
    ): T {

        // outbox 패턴 시작
        val outbox = OutboxEntity.start(
            payload = payload,
            aggregateId = aggregateId
        )

        outboxRepository.save(outbox)

        val action = action.invoke()

        outbox.confirm()

        return action
    }

    companion object {
        private const val ORDER_CREATED_ID = "CreatedOrder"
    }
}
