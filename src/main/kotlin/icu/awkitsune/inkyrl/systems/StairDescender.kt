package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.Exit
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.attributes.types.StairsDown
import icu.awkitsune.inkyrl.attributes.types.sardiniumCounter
import icu.awkitsune.inkyrl.blocks.GameBlock
import icu.awkitsune.inkyrl.events.PlayerWonTheGame
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.MoveDown
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {

    override suspend fun receive(message: MoveDown): Response {
        val (context, source) = message
        val world = context.world
        val pos = source.position
        world.fetchBlockAt(pos).map { block ->
            when {
                block.hasStairsDown -> {
                    logGameEvent("You move down one level...", StairDescender)
                    world.moveEntity(source, pos.withRelativeZ(-1))
                    world.scrollOneDown()
                }
                block.hasExit -> source.whenTypeIs<Player> {    // 1
                    Zircon.eventBus.publish(PlayerWonTheGame(it.sardiniumCounter.sardiniumCount, StairDescender))
                }
                else -> logGameEvent("You search for a trapdoor, but you find nothing.", StairDescender)
            }
        }
        return Consumed
    }

    private val GameBlock.hasStairsDown: Boolean
        get() = this.entities.any { it.type == StairsDown }

    private val GameBlock.hasExit: Boolean              // 2
        get() = this.entities.any { it.type == Exit }
}
