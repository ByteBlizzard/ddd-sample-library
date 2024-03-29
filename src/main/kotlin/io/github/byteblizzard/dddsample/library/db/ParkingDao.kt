package io.github.byteblizzard.dddsample.library.db

import org.springframework.data.jpa.repository.JpaRepository


interface ParkingDao : JpaRepository<ParkingTable, String> {

}