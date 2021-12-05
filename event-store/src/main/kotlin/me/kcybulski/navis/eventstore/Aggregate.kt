package me.kcybulski.navis.eventstore

abstract class Aggregate<T : Aggregate<T>>(
    internal val unpublishedEvents: List<Event>
) {

    fun event(event: Event) = apply(event)
        .withUnpublishedEvent(event)

    abstract fun apply(event: Event): T

    abstract fun withUnpublishedEvent(event: Event): T

    abstract fun withoutEvents(): T

}
