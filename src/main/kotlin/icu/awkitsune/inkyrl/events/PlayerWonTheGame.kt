package icu.awkitsune.inkyrl.events

import org.hexworks.cobalt.events.api.Event

data class PlayerWonTheGame(
        val sardiniums: Int,
        override val emitter: Any
) : Event