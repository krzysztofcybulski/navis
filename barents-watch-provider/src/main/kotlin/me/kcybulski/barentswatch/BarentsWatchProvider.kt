package me.kcybulski.barentswatch

import me.kcybulski.navis.ships.Course
import me.kcybulski.navis.ships.Destination.SomeDestination
import me.kcybulski.navis.ships.Destination.UnknownDestination
import me.kcybulski.navis.ships.Eta
import me.kcybulski.navis.ships.Knots
import me.kcybulski.navis.ships.MMSI
import me.kcybulski.navis.ships.Position
import me.kcybulski.navis.ships.RegisterShipMovementCommand
import me.kcybulski.navis.ships.ShipType
import me.kcybulski.navis.ships.ShipType.CARGO
import me.kcybulski.navis.ships.ShipType.FISHING
import me.kcybulski.navis.ships.ShipType.PASSENGER
import me.kcybulski.navis.ships.ShipType.SAILING
import me.kcybulski.navis.ships.ShipType.TANKER
import me.kcybulski.navis.ships.ShipType.TOWING
import me.kcybulski.navis.ships.ShipType.UNKNOWN
import me.kcybulski.navis.ships.ShipsFacade

class BarentsWatchProvider internal constructor(
    private val ships: ShipsFacade,
    private val client: BarentsWatchClient
) {

    fun provideShips() {
        client.getAllShips()
            .map(Response::toCommand)
            .forEach(ships::registerShipMovementPosition)
    }

}

private fun Response.toCommand() = RegisterShipMovementCommand(
    mmsi = MMSI(mmsi),
    name = name ?: "-",
    type = shipType.toShipType(),
    position = Position(geometry.coordinates[0], geometry.coordinates[1]),
    courseOverGround = Course(cog),
    speedOverGround = Knots(sog),
    destination = destination?.let(::SomeDestination) ?: UnknownDestination,
    estimatedTimeOfArrival = Eta.UnknownEta
)

private fun Int.toShipType() = when(this) {
    30 -> FISHING
    31, 32 -> TOWING
    36 -> SAILING
    60, 61, 62, 63, 64, 65, 66, 67, 68, 69 -> PASSENGER
    70, 71, 72, 73, 74 -> CARGO
    80, 81, 82, 83, 84 -> TANKER
    else -> UNKNOWN
}
