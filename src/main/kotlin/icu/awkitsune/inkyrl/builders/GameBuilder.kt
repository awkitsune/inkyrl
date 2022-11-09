package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.GameConfig.LOG_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.SIDEBAR_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WINDOW_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.WINDOW_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WORLD_SIZE
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.world.Game
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class GameBuilder(val worldSize: Size3D) {

    private val visibleSize = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_HEIGHT,
        zLength = 1
    )

    val world = WorldBuilder(worldSize)
        .makeCaves()
        .build(visibleSize = visibleSize)

    fun buildGame() : Game {

        prepareWorld()

        val player = addPlayer()

        return Game.create(
            player = player,
            world = world
        )
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer() : GameEntity<Player> {
        val player = EntityFactory.newPlayer()
        world.addAtEmptyPosition(
            player,
            offset = Position3D.create(0, 0, GameConfig.DUNGEON_LEVELS - 1),
            size = world.visibleSize.copy(zLength = 0)
        )

        return player
    }

    companion object {

        fun create() = GameBuilder(
            worldSize = WORLD_SIZE
        ).buildGame()
    }
}