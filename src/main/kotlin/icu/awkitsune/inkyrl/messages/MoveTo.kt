package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.GameMessage
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveTo (
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    val position: Position3D
) : GameMessage