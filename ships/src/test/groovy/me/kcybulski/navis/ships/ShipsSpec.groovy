package me.kcybulski.navis.ships

import me.kcybulski.navis.ships.infrastructure.ShipsEventSourceRepository
import spock.lang.Specification

import java.time.Clock

import static me.kcybulski.navis.eventstore.EventStoreConfigurationKt.inMemoryEventStore
import static me.kcybulski.navis.ships.RegisterShipMovementCommandBuilder.registerShipMovementCommand
import static me.kcybulski.navis.ships.ShipAssertions.assertThat

class ShipsSpec extends Specification {

    private def eventStore = inMemoryEventStore()
    private def clock = Clock.systemUTC()
    private def repository = new ShipsEventSourceRepository(eventStore)
    private def ships = new ShipsFacade(repository, new ShipFactory(clock), clock)

    def 'should register new ship'() {
        given:
            def command = registerShipMovementCommand {
                mmsi '123456'
                name 'JOHN WICK'
                latitude 3.00
                longitude 5.30
            }
        when:
            ships.registerShipMovementPosition(command)
        then:
            assertThat('123456', repository)
                    .hasName('JOHN WICK')
                    .isAt(5.30, 3.00)
    }

    def 'should move existing ship without changing its name'() {
        given:
            ships.registerShipMovementPosition(registerShipMovementCommand {
                mmsi '123456'
                name 'JOHN WICK'
            })
        and:
            def secondCommand = registerShipMovementCommand {
                mmsi '123456'
                name 'ALICE'
                latitude 9.00
                longitude 9.00
            }
        when:
            ships.registerShipMovementPosition(secondCommand)
        then:
            assertThat('123456', repository)
                    .hasName('JOHN WICK')
                    .isAt(9.00, 9.00)
    }

}
