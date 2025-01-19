package com.example.kafkastreampushsample.domain.repository

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "outbox")
class OutboxEntity(
    @Id
    @Column(name = "outbox_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val eventId: String = UUID.randomUUID().toString(),
    val aggregateId: String,
    val payload: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var success: Boolean = false
) {
    constructor() : this(
        id = null,
        aggregateId = "",
        payload = ""
    )

    companion object {
        fun start(
            payload: String,
            aggregateId: String
        ): OutboxEntity = OutboxEntity(
            payload = payload,
            aggregateId = aggregateId
        )
    }

    fun confirm() {
        this.success = false
    }
}
