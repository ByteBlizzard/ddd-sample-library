package io.github.byteblizzard.dddsample.library.db

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class EventRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val eventType: String,
    val aggregateType: String,
    val aggregateId: String,
    val eventTime: LocalDateTime = LocalDateTime.now(),
    val content: String
)