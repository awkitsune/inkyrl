package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.StairsDown
import icu.awkitsune.inkyrl.blocks.GameBlock
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.MoveDown
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {

    override suspend fun receive(message: MoveDown): Response {
        val (context, player) = message
        val world = context.world
        val playerPos = player.position

        world.fetchBlockAt(playerPos).map { block ->
            if (block.hasStairsDown) {
                logGameEvent("You move down one level...", StairDescender)
                world.moveEntity(player, playerPos.withRelativeZ(-1))
                world.scrollOneDown()
            } else {
                logGameEvent("You search for a trapdoor, but you find nothing.", StairDescender)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }
}