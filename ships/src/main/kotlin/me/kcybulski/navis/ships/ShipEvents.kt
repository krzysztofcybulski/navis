package me.kcybulski.navis.ships

import me.kcybulski.navis.eventstore.Event
import java.time.Instant

data class ShipRegistered(
    val mmsi: MMSI,
    val name: String,
    val type: ShipType,
    val registeredAt: Instant
) : Event("SHIP_REGISTERED", mmsi.raw, registeredAt)

data class ShipSailedOut(
    val ship: MMSI,
    val position: Position,
    val courseOverGround: Course,
    val speedOverGround: Knots,
    val destination: Destination,
    val estimatedTimeOfArrival: Eta,
    val sailedOutAt: Instant
) : Event("SHIP_SAILED_OUT", ship.raw, sailedOutAt)

data class ShipMovedTo(
    val ship: MMSI,
    val position: Position,
    val speedOverGround: Knots,
    val movedAt: Instant
) : Event("SHIP_MOVED_TO", ship.raw, movedAt)

data class ShipChangedCourse(
    val ship: MMSI,
    val courseOverGround: Course,
    val speedOverGround: Knots,
    val estimatedTimeOfArrival: Eta,
    val changedAt: Instant
) : Event("SHIP_CHANGED_COURSE", ship.raw, changedAt)

data class ShipChangedDestination(
    val ship: MMSI,
    val destination: Destination,
    val estimatedTimeOfArrival: Eta,
    val changedAt: Instant
) : Event("SHIP_CHANGED_DESTINATION", ship.raw, changedAt)
