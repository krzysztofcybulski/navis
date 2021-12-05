package me.kcybulski.navis.eventstore

internal class InMemoryEventsRepository : EventsRepository {

    private val memory: MutableMap<String, List<Event>> = mutableMapOf()

    override fun save(events: Collection<Event>) {
        events.forEach { event -> memory.merge(event.businessKey, listOf(event), List<Event>::plus) }
    }

    override fun load(businessKey: String): List<Event> = memory[businessKey] ?: emptyList()

}
