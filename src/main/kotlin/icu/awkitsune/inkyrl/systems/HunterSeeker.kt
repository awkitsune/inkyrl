package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.messages.MoveTo
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object HunterSeeker : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (world, _, _, player) = context
        var hunted = false
        val path = world.findPath(entity, player)
        if (path.isNotEmpty()) {                    // 1
            entity.receiveMessage(
                MoveTo(           // 2
                    context = context,
                    source = entity,
                    position = path.iterator().next().toPosition3D(player.position.z))
            )
            hunted = true
        }
        return hunted   // 3
    }
}