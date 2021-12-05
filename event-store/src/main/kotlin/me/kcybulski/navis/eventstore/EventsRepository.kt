package me.kcybulski.navis.eventstore

interface EventsRepository {

    fun save(events: Collection<Event>)
    fun load(businessKey: String): List<Event>

}
