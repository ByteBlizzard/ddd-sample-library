package io.github.byteblizzard.dddsample.library

import io.github.byteblizzard.dddsample.library.domain.用户.用户聚合实现
import org.axonframework.common.jdbc.PersistenceExceptionResolver
import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventsourcing.EventSourcingRepository
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore
import org.axonframework.eventsourcing.eventstore.EventStorageEngine
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine
import org.axonframework.modelling.command.Repository
import org.axonframework.serialization.Serializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class DddParkingApplication

fun main(args: Array<String>) {
    runApplication<DddParkingApplication>(*args)
}

@Configuration
class config {
    @Bean
    fun 用户聚合仓储(eventStore: EventStore?): Repository<用户聚合实现> {
        return EventSourcingRepository.builder(用户聚合实现::class.java).eventStore(eventStore).build()
    }

    @Bean
    fun eventStore(
        storageEngine: EventStorageEngine,
    ): EventStore {
        return EmbeddedEventStore.builder()
            .storageEngine(storageEngine)
            .build()
    }

    @Bean
    fun eventStorageEngine(
        serializer: Serializer,
        persistenceExceptionResolver: PersistenceExceptionResolver,
        @Qualifier("eventSerializer") eventSerializer: Serializer,
        entityManagerProvider: EntityManagerProvider,
        transactionManager: TransactionManager
    ): EventStorageEngine {
        return JpaEventStorageEngine.builder()
            .snapshotSerializer(serializer)
            .persistenceExceptionResolver(persistenceExceptionResolver)
            .eventSerializer(eventSerializer)
            .entityManagerProvider(entityManagerProvider)
            .transactionManager(transactionManager)
            .build()
    }
}