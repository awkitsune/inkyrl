package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.builders.GameTileRepository
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

object FogOfWar : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (world, _, _, player) = context
        world.findVisiblePositionsFor(player).forEach { pos ->
            world.fetchBlockAt(
                Position3D.create(
                    x = pos.x,
                    y = pos.y,
                    z = player.position.z
                )
            ).map { block ->
                block.top = GameTileRepository.EMPTY
            }
        }
        return true
    }
}
