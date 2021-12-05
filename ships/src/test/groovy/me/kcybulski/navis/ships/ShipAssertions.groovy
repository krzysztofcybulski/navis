package me.kcybulski.navis.ships

import me.kcybulski.navis.eventstore.EventStore

class ShipAssertions {

    private final Ship ship

    ShipAssertions(Ship ship) {
        this.ship = ship
    }

    static def assertThat(MMSI mmsi, ShipsRepository shipsRepository) {
        return new ShipAssertions(shipsRepository.find(mmsi))
    }

    static def assertThat(String mmsi, ShipsRepository shipsRepository) {
        return assertThat(new MMSI(mmsi), shipsRepository)
    }

    def hasName(String name) {
        assert ship.name == name
        return this
    }

    def isAt(Double longitude, Double latitude) {
        assert ship instanceof MovingShip
        assert ship.position.longitude == longitude
        assert ship.position.latitude == latitude
        return this
    }

}
