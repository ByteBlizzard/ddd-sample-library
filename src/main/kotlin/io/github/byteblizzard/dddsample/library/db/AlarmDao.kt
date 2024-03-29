package io.github.byteblizzard.dddsample.library.db

import org.springframework.data.jpa.repository.JpaRepository

interface AlarmDao : JpaRepository<AlarmTable, Long> {
}