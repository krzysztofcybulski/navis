package me.kcybulski.navis.ships.infrastructure

import me.kcybulski.navis.eventstore.Event
import me.kcybulski.navis.eventstore.EventStore
import me.kcybulski.navis.ships.MMSI
import me.kcybulski.navis.ships.RegisteredShip
import me.kcybulski.navis.ships.Ship
import me.kcybulski.navis.ships.ShipRegistered
import me.kcybulski.navis.ships.ShipsRepository

class ShipsEventSourceRepository(
    private val eventStore: EventStore
) : ShipsRepository {

    override fun find(mmsi: MMSI): Ship? = eventStore.loadAggregate(mmsi.raw) { event: Event ->
        with(event as ShipRegistered) { RegisteredShip(event.mmsi, event.name) }
    }

    override fun save(ship: Ship): Ship = eventStore.saveAggregate(ship)
}
