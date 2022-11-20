package icu.awkitsune.inkyrl.events

import org.hexworks.cobalt.events.api.Event

data class PlayerDied(
        val cause: String,
        override val emitter: Any
) : Event