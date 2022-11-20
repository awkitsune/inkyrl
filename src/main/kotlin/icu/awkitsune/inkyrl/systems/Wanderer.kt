package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.extensions.sameLevelNeighborsShuffled
import icu.awkitsune.inkyrl.messages.MoveTo
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object Wanderer : BaseBehavior<GameContext>() {

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val pos = entity.position
        if (pos.isUnknown.not()) {      // 1
            entity.receiveMessage(      // 2
                MoveTo(
                    context = context,
                    source = entity,
                    position = pos.sameLevelNeighborsShuffled().first() // 3
                )
            )
            return true
        }
        return false
    }
}
