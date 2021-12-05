package me.kcybulski.navis.ships

import java.time.Clock

class ShipFactory(
    private val clock: Clock
) {

    fun create(mmsi: MMSI, name: String, type: ShipType) = RegisteredShip(mmsi, name)
        .withUnpublishedEvent(ShipRegistered(mmsi, name, type, clock.instant()))

}
