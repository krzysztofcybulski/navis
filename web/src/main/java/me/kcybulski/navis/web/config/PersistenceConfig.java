package me.kcybulski.navis.web.config;

import me.kcybulski.navis.web.snapshots.CacheSnapshotsReadModel;
import me.kcybulski.navis.web.snapshots.ShipSnapshot;
import me.kcybulski.navis.web.snapshots.ShipSnapshotsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;

@Configuration
@EnableScheduling
public class PersistenceConfig {

    Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

    private final CacheSnapshotsReadModel cacheSnapshots;
    private final ShipSnapshotsRepository snapshotsRepository;

    public PersistenceConfig(CacheSnapshotsReadModel cacheSnapshots, ShipSnapshotsRepository snapshotsRepository) {
        this.cacheSnapshots = cacheSnapshots;
        this.snapshotsRepository = snapshotsRepository;
    }

//    @Scheduled(fixedDelay = 10000)
//    public void scheduleSaving() {
//        Collection<ShipSnapshot> snapshots = cacheSnapshots.getSnapshots();
//        snapshotsRepository.saveAllAndFlush(snapshots);
//        logger.info("Saved {} snapshots", snapshots.size());
//    }
}
