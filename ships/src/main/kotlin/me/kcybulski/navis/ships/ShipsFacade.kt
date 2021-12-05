package me.kcybulski.navis.ships

import java.time.Clock
import java.time.Instant

class ShipsFacade(
    private val repository: ShipsRepository,
    private val factory: ShipFactory,
    private val clock: Clock
) {

    fun registerShipMovementPosition(command: RegisterShipMovementCommand) {
        findOrCreate(command)
            .move(command, clock.instant())
            .let(repository::save)
    }

    private fun findOrCreate(command: RegisterShipMovementCommand) = repository
        .find(command.mmsi)
        ?: factory.create(command.mmsi, command.name, command.type)

}

private fun Ship.move(command: RegisterShipMovementCommand, timestamp: Instant) = when (this) {
    is RegisteredShip -> moveTo(command.toMoveCommand(timestamp))
    is MovingShip -> moveTo(command.toMoveCommand(timestamp))
}

private fun RegisterShipMovementCommand.toMoveCommand(timestamp: Instant) = MoveShipCommand(
    mmsi,
    position,
    courseOverGround,
    speedOverGround,
    destination,
    estimatedTimeOfArrival,
    timestamp
)


data class RegisterShipMovementCommand(
    val mmsi: MMSI,
    val name: String,
    val type: ShipType,
    val position: Position,
    val courseOverGround: Course,
    val speedOverGround: Knots,
    val destination: Destination,
    val estimatedTimeOfArrival: Eta
)

data class Course(val degree: Double)

data class Knots(val raw: Double)

data class MMSI(val raw: String)

sealed class Destination {

    object UnknownDestination : Destination()
    data class SomeDestination(val name: String) : Destination()

}

data class Position(val longitude: Double, val latitude: Double)

sealed class Eta {

    object UnknownEta : Eta()
    data class ArrivalAt(val time: Instant) : Eta()

}
