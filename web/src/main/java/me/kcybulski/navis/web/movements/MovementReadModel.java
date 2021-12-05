package me.kcybulski.navis.web.movements;

import me.kcybulski.navis.ships.ShipChangedCourse;
import me.kcybulski.navis.ships.ShipMovedTo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class MovementReadModel {

    private final Sinks.Many<ShipMovement> shipMovements = Sinks.many()
            .multicast()
            .directBestEffort();

    public Flux<ShipMovement> getShipMovements() {
        return shipMovements.asFlux();
    }

    public void onMoved(ShipMovedTo shipMovedTo) {
        shipMovements.tryEmitNext(
                new ShipMove(
                        shipMovedTo.getShip(),
                        shipMovedTo.getPosition(),
                        shipMovedTo.getSpeedOverGround()
                )
        );
    }

    public void onChangedCourse(ShipChangedCourse shipChangedCourse) {
        shipMovements.tryEmitNext(
                new ShipRotation(
                        shipChangedCourse.getShip(),
                        shipChangedCourse.getCourseOverGround(),
                        shipChangedCourse.getSpeedOverGround()
                )
        );
    }

}
