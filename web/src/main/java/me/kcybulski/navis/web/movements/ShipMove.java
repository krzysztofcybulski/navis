package me.kcybulski.navis.web.movements;

import me.kcybulski.navis.ships.Knots;
import me.kcybulski.navis.ships.MMSI;
import me.kcybulski.navis.ships.Position;

public record ShipMove(MMSI mmsi, Position position, Knots speed) implements ShipMovement {

}
