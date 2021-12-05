package me.kcybulski.navis.ships

import java.time.Instant

data class MoveShipCommand(
    val mmsi: MMSI,
    val position: Position,
    val courseOverGround: Course,
    val speedOverGround: Knots,
    val destination: Destination,
    val estimatedTimeOfArrival: Eta,
    val movedAt: Instant
)
