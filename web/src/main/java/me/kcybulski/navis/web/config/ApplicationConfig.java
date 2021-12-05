package me.kcybulski.navis.web.config;

import me.kcybulski.barentswatch.BarentsWatchProvider;
import me.kcybulski.navis.eventstore.EventHandler;
import me.kcybulski.navis.eventstore.EventStore;
import me.kcybulski.navis.ships.*;
import me.kcybulski.navis.web.movements.MovementReadModel;
import me.kcybulski.navis.web.snapshots.CacheSnapshotsReadModel;
import me.kcybulski.navis.web.snapshots.ShipSnapshot;
import me.kcybulski.navis.web.snapshots.ShipSnapshotsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.stream.StreamSupport.stream;
import static me.kcybulski.barentswatch.BarentsWatchConfigurationKt.barentsWatch;
import static me.kcybulski.navis.eventstore.EventStoreConfigurationKt.inMemoryEventStore;
import static me.kcybulski.navis.ships.ShipsConfigurationKt.ships;

@Configuration
public class ApplicationConfig {

    @Bean
    CacheSnapshotsReadModel cacheSnapshotsReadModel(ShipSnapshotsRepository repository) {
        List<ShipSnapshot> allSnapshots = stream(repository.findAll().spliterator(), false).toList();
        return new CacheSnapshotsReadModel(allSnapshots);
    }

    @Bean
    MovementReadModel movementReadModel() {
        return new MovementReadModel();
    }

    @Bean
    EventStore eventStore(
            CacheSnapshotsReadModel snapshots,
            MovementReadModel movements
    ) {
        return inMemoryEventStore(
                new EventHandler<ShipRegistered>("SHIP_REGISTERED", snapshots::onRegister),
                new EventHandler<ShipSailedOut>("SHIP_SAILED_OUT", snapshots::onSailedOut),
                new EventHandler<ShipMovedTo>("SHIP_MOVED_TO", snapshots::onMoved),
                new EventHandler<ShipMovedTo>("SHIP_MOVED_TO", movements::onMoved),
                new EventHandler<ShipChangedCourse>("SHIP_CHANGED_COURSE", snapshots::onChangedCourse),
                new EventHandler<ShipChangedCourse>("SHIP_CHANGED_COURSE", movements::onChangedCourse),
                new EventHandler<ShipChangedDestination>("SHIP_CHANGED_DESTINATION", snapshots::onShipChangedDestination)
        );
    }

    @Bean
    ShipsFacade shipsFacade(EventStore eventStore) {
        return ships(eventStore);
    }

    @Bean
    BarentsWatchProvider barentsWatchProvider(ShipsFacade ships) {
        return barentsWatch(ships);
    }

}
