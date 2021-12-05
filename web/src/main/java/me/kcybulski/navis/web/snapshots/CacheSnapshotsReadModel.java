package me.kcybulski.navis.web.snapshots;

import me.kcybulski.navis.ships.*;
import me.kcybulski.navis.ships.Destination.SomeDestination;
import me.kcybulski.navis.ships.Eta.ArrivalAt;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class CacheSnapshotsReadModel {

    private final Map<String, ShipSnapshot> cache;

    public CacheSnapshotsReadModel(List<ShipSnapshot> cache) {
        this.cache = cache.stream()
                .collect(Collectors.toMap(ShipSnapshot::getMmsi, identity()));
    }

    public Collection<ShipSnapshot> getSnapshots() {
        return cache.values();
    }

    public void onRegister(ShipRegistered shipRegistered) {
        if(!cache.containsKey(shipRegistered.getMmsi().getRaw())) {
            cache.put(shipRegistered.getMmsi().getRaw(), new ShipSnapshot(shipRegistered.getMmsi().getRaw(), shipRegistered.getName(), shipRegistered.getType().name()));
        }
    }

    public void onSailedOut(ShipSailedOut shipSailedOut) {
        if (cache.containsKey(shipSailedOut.getShip().getRaw())) {
            ShipSnapshot ship = cache.get(shipSailedOut.getShip().getRaw());
            ship.setDestination(destination(shipSailedOut.getDestination()));
            ship.setEta(eta(shipSailedOut.getEstimatedTimeOfArrival()));
            ship.setLatitude(shipSailedOut.getPosition().getLatitude());
            ship.setLongitude(shipSailedOut.getPosition().getLongitude());
            ship.setSog(shipSailedOut.getSpeedOverGround().getRaw());
            ship.setCog(shipSailedOut.getCourseOverGround().getDegree());
            ship.setLastModifiedAt(shipSailedOut.getSailedOutAt());
        }
    }

    public void onMoved(ShipMovedTo shipMovedTo) {
        if (cache.containsKey(shipMovedTo.getShip().getRaw())) {
            ShipSnapshot ship = cache.get(shipMovedTo.getShip().getRaw());
            ship.setLatitude(shipMovedTo.getPosition().getLatitude());
            ship.setLongitude(shipMovedTo.getPosition().getLongitude());
            ship.setSog(shipMovedTo.getSpeedOverGround().getRaw());
            ship.setLastModifiedAt(shipMovedTo.getMovedAt());
        }
    }

    public void onChangedCourse(ShipChangedCourse shipChangedCourse) {
        if (cache.containsKey(shipChangedCourse.getShip().getRaw())) {
            ShipSnapshot ship = cache.get(shipChangedCourse.getShip().getRaw());
            ship.setEta(eta(shipChangedCourse.getEstimatedTimeOfArrival()));
            ship.setSog(shipChangedCourse.getSpeedOverGround().getRaw());
            ship.setCog(shipChangedCourse.getCourseOverGround().getDegree());
        }

    }

    public void onShipChangedDestination(ShipChangedDestination shipChangedDestination) {
        if (cache.containsKey(shipChangedDestination.getShip().getRaw())) {
            ShipSnapshot ship = cache.get(shipChangedDestination.getShip().getRaw());
            ship.setDestination(destination(shipChangedDestination.getDestination()));
            ship.setEta(eta(shipChangedDestination.getEstimatedTimeOfArrival()));
        }
    }

    private String destination(Destination destination) {
        if (destination instanceof SomeDestination)
            return ((SomeDestination) destination).getName();
        return null;
    }

    private Instant eta(Eta eta) {
        if (eta instanceof ArrivalAt)
            return ((ArrivalAt) eta).getTime();
        return null;
    }
}
