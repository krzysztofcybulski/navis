package me.kcybulski.navis.ships

import me.kcybulski.navis.eventstore.Aggregate
import me.kcybulski.navis.eventstore.Event

sealed class Ship(
    open val mmsi: MMSI,
    open val name: String,
    open val unpublishedEvents: List<Event> = emptyList()
) : Aggregate<Ship>(unpublishedEvents)

data class RegisteredShip(
    override val mmsi: MMSI,
    override val name: String,
    override val unpublishedEvents: List<Event> = emptyList()
) : Ship(mmsi, name, unpublishedEvents) {

    fun moveTo(command: MoveShipCommand) = event(
        ShipSailedOut(
            ship = mmsi,
            position = command.position,
            courseOverGround = command.courseOverGround,
            speedOverGround = command.speedOverGround,
            destination = command.destination,
            estimatedTimeOfArrival = command.estimatedTimeOfArrival,
            sailedOutAt = command.movedAt
        )
    )

    override fun apply(event: Event): Ship = when (event) {
        is ShipSailedOut -> MovingShip(
            mmsi,
            name,
            event.position,
            event.courseOverGround,
            event.speedOverGround,
            event.destination,
            event.estimatedTimeOfArrival,
            unpublishedEvents
        )
        else -> this
    }

    override fun withUnpublishedEvent(event: Event): Ship = copy(unpublishedEvents = unpublishedEvents + event)

    override fun withoutEvents(): Ship = copy(unpublishedEvents = emptyList())

}


data class MovingShip(
    override val mmsi: MMSI,
    override val name: String,
    val position: Position,
    val courseOverGround: Course,
    val speedOverGround: Knots,
    val destination: Destination,
    val estimatedTimeOfArrival: Eta,
    override val unpublishedEvents: List<Event> = emptyList()
) : Ship(mmsi, name, unpublishedEvents) {

    fun moveTo(command: MoveShipCommand) =
        eventIf(command.movedToEvent(mmsi)) { position != command.position }
            .eventIf(command.changedCourseEvent(mmsi)) { courseOverGround != command.courseOverGround }
            .eventIf(command.changedDestinationEvent(mmsi) ) { destination != command.destination }

    override fun apply(event: Event): MovingShip = when (event) {
        is ShipMovedTo -> copy(position = event.position)
        is ShipChangedCourse -> copy(
            courseOverGround = event.courseOverGround,
            speedOverGround = event.speedOverGround,
            estimatedTimeOfArrival = event.estimatedTimeOfArrival
        )
        is ShipChangedDestination -> copy(
            destination = event.destination,
            estimatedTimeOfArrival = event.estimatedTimeOfArrival
        )
        else -> this
    }

    override fun withUnpublishedEvent(event: Event): MovingShip = copy(unpublishedEvents = unpublishedEvents + event)

    override fun withoutEvents(): MovingShip = copy(unpublishedEvents = emptyList())

}

private fun MoveShipCommand.movedToEvent(mmsi: MMSI) = ShipMovedTo(mmsi, position, speedOverGround, movedAt)

private fun MoveShipCommand.changedDestinationEvent(mmsi: MMSI) =
    ShipChangedDestination(mmsi, destination, estimatedTimeOfArrival, movedAt)

private fun MoveShipCommand.changedCourseEvent(mmsi: MMSI) =
    ShipChangedCourse(mmsi, courseOverGround, speedOverGround, estimatedTimeOfArrival, movedAt)

private fun Ship.eventIf(event: Event, condition: () -> Boolean) = if (condition()) event(event) else this
