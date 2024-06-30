package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.availablebooks.AvailableBooksImpl
import org.springframework.data.jpa.repository.JpaRepository

interface AvailableBooksDao: JpaRepository<AvailableBooksImpl, String> {

}