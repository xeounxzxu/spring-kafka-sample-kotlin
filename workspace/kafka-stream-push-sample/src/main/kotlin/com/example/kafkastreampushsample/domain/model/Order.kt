package com.example.kafkastreampushsample.domain.model

import com.example.kafkastreampushsample.domain.repository.OrderEntity

data class Order(
    val customerId: Long,
    val total: Long
) {

    fun toEntity(): OrderEntity {
        return OrderEntity(
            customerId = this.customerId,
            total = this.total
        )
    }

    fun toPayload(): String {
        return "string"
    }
}
