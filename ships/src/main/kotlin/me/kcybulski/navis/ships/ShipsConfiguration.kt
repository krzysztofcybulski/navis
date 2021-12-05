package me.kcybulski.navis.ships

import me.kcybulski.navis.eventstore.EventStore
import me.kcybulski.navis.ships.infrastructure.ShipsEventSourceRepository
import java.time.Clock

@JvmOverloads
fun ships(eventStore: EventStore, clock: Clock = Clock.systemUTC()) = ShipsFacade(
    repository = ShipsEventSourceRepository(eventStore),
    factory = ShipFactory(clock),
    clock = clock
)
