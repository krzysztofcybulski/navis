package me.kcybulski.navis.web.api;

import me.kcybulski.navis.web.snapshots.CacheSnapshotsReadModel;
import me.kcybulski.navis.web.snapshots.ShipSnapshot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
@RequestMapping("ships")
public class ShipsController {

    private final CacheSnapshotsReadModel shipSnapshots;

    public ShipsController(CacheSnapshotsReadModel shipSnapshots) {
        this.shipSnapshots = shipSnapshots;
    }

    @GetMapping()
    Collection<ShipSnapshot> getShips() {
        return shipSnapshots.getSnapshots();
    }
}
