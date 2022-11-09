package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.GameMessage
import org.hexworks.amethyst.api.entity.EntityType

interface EntityAction<S : EntityType, T: EntityType> : GameMessage {

    val target: GameEntity<T>

    operator fun component1() = context
    operator fun component2() = source
    operator fun component3() = target
}