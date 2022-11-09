package icu.awkitsune.inkyrl.attributes

import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.messages.EntityAction
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

class EntityActions (
    private vararg val actions: KClass<out EntityAction<out EntityType, out EntityType>>
) : BaseAttribute() {
    fun createActionsFor(
        context: GameContext,
        source: GameEntity<EntityType>,
        target: GameEntity<EntityType>
    ) : Iterable<EntityAction<out EntityType, out EntityType>> {
        return actions.map {
            try {
                it.constructors.first().call(context, source, target)
            } catch (e: Exception) {
                throw IllegalArgumentException("Can't create EntityAction. Does it have a proper constructor?")
            }
        }
    }
}