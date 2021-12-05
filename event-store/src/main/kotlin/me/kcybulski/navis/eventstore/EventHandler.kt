package me.kcybulski.navis.eventstore

import java.util.function.Consumer

class EventHandler<T : Event>(val type: String, val handle: Consumer<T>)
