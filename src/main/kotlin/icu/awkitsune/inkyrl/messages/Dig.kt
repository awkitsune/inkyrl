package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Dig (
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    override val target: GameEntity<EntityType>
) : EntityAction<EntityType, EntityType>