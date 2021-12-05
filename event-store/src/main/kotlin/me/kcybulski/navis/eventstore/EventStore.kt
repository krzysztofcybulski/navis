package me.kcybulski.navis.eventstore

import mu.KotlinLogging

class EventStore internal constructor(
    private val eventsRepository: EventsRepository,
    private val handlers: List<EventHandler<out Event>>
) {
    private val logger = KotlinLogging.logger {}

    fun <T : Aggregate<T>> loadAggregate(businessKey: String, recreate: (Event) -> T): T? = eventsRepository
        .load(businessKey)
        .takeIf { it.isNotEmpty() }
        ?.run {
            fold(recreate(first())) { agg, event -> agg.apply(event) }
        }

    fun <T : Aggregate<T>> saveAggregate(aggregate: T): T = eventsRepository
        .save(aggregate.unpublishedEvents)
        .let { aggregate.withoutEvents() }
        .also { runHandlers(aggregate) }
        .also { logEvents(aggregate) }

    fun eventStream(businessKey: String): List<Event> = eventsRepository
        .load(businessKey)

    private fun runHandlers(aggregate: Aggregate<*>) {
        aggregate.unpublishedEvents
            .forEach { event: Event -> findHandlers(event).forEach { handler -> handler.handle.accept(event) } }
    }

    private fun findHandlers(event: Event): List<EventHandler<Event>> = handlers.filter { it.type == event.eventType } as List<EventHandler<Event>>

    private fun logEvents(aggregate: Aggregate<*>) {
        aggregate.unpublishedEvents
            .forEach { logger.info { "${it.businessKey} ${it.eventType}: $it" } }
    }

}
