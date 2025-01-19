package com.example.kafkastreampushsample.domain.repository

import jakarta.persistence.*

@Entity
@Table(name = "order")
class OrderEntity(
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val customerId: Long,
    val total: Long,
) {
    constructor() : this(
        null,
        0L,
        0L
    )
}
