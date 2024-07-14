package io.github.byteblizzard.dddsample.library.db

import org.springframework.data.jpa.repository.JpaRepository

interface EventRecordDao : JpaRepository<EventRecord, Long> {
}