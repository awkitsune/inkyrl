package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.extensions.GameItemHolder
import icu.awkitsune.inkyrl.extensions.GameMessage
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.zircon.api.data.Position3D

data class InspectInventory(
    override val context: GameContext,
    override val source: GameItemHolder,
    val position: Position3D
) : GameMessage
