package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.StairsUp
import icu.awkitsune.inkyrl.blocks.GameBlock
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.MoveUp
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object StairClimber : BaseFacet<GameContext, MoveUp>(MoveUp::class) {

    override suspend fun receive(message: MoveUp): Response {
        val (context, player) = message
        val world = context.world
        val playerPos = player.position
        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsUp) {                                            // 1
                logGameEvent("You move up one level...", StairClimber)
                world.moveEntity(player, playerPos.withRelativeZ(1))    // 2
                world.scrollOneUp()
            } else {
                logGameEvent("You jump up and try to reach the ceiling. You fail.", StairClimber) // 3
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsUp: Boolean                                  // 4
        get() = this.entities.any { it.type == StairsUp }
}
