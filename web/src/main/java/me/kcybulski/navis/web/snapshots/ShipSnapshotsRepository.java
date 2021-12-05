package me.kcybulski.navis.web.snapshots;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipSnapshotsRepository extends JpaRepository<ShipSnapshot, String> {
}
