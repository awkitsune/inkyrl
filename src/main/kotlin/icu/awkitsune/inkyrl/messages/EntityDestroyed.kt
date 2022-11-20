package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.GameMessage
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class EntityDestroyed(
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    val destroyer: GameEntity<EntityType>
) : GameMessage