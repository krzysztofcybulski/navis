package me.kcybulski.navis.eventstore

import java.time.Instant

abstract class Event(
    val eventType: String,
    val businessKey: String,
    val timestamp: Instant
)
