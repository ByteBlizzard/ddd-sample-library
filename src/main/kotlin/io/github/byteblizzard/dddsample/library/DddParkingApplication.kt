package io.github.byteblizzard.dddsample.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DddParkingApplication

fun main(args: Array<String>) {
    runApplication<DddParkingApplication>(*args)
}
