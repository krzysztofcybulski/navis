package me.kcybulski.navis.web.movements;

import me.kcybulski.navis.ships.Course;
import me.kcybulski.navis.ships.Knots;
import me.kcybulski.navis.ships.MMSI;

public record ShipRotation(MMSI mmsi, Course course, Knots speed) implements ShipMovement {

}
