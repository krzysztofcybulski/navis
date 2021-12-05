package me.kcybulski.navis.ships

interface ShipsRepository {

    fun find(mmsi: MMSI): Ship?
    fun save(ship: Ship): Ship

}
