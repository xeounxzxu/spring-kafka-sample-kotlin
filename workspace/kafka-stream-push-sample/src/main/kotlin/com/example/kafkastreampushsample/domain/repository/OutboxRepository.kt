package com.example.kafkastreampushsample.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OutboxRepository : JpaRepository<OutboxEntity, Long>
