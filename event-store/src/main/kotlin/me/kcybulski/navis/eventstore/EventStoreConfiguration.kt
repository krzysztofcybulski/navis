package me.kcybulski.navis.eventstore

fun inMemoryEventStore(
    vararg handlers: EventHandler<out Event>
) = EventStore(InMemoryEventsRepository(), handlers.toList())

fun customEventStore(
    eventsRepository: EventsRepository,
    vararg handlers: EventHandler<out Event>
) = EventStore(eventsRepository, handlers.toList())
