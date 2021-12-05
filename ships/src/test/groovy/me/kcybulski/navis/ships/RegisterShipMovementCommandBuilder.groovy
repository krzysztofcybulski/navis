package me.kcybulski.navis.ships

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.time.Instant

import static me.kcybulski.navis.ships.ShipType.CARGO

@Builder(builderStrategy = SimpleStrategy, prefix = '')
class RegisterShipMovementCommandBuilder {

    private static Random RANDOM = new Random()

    String mmsi = RANDOM.nextInt(1000000)
    String name = 'White wolf'
    ShipType type = CARGO
    Double latitude = 0.00
    Double longitude = 0.00
    Double cog = 0.00
    Double sog = 0.00
    String destination = 'HOME'
    Instant eta = Instant.now()

    static def registerShipMovementCommand(@DelegatesTo(RegisterShipMovementCommandBuilder) builder) {
        def command = new RegisterShipMovementCommandBuilder()
        command.with(builder)
        return command.build()
    }

    private def build() {
        return new RegisterShipMovementCommand(
                new MMSI(mmsi),
                name,
                type,
                new Position(longitude, latitude),
                new Course(cog),
                new Knots(sog),
                new Destination.SomeDestination(destination),
                new Eta.ArrivalAt(eta)
        )
    }

}
