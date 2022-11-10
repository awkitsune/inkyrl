package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.GameConfig.FUNGI_PER_LEVEL
import icu.awkitsune.inkyrl.GameConfig.LOG_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.SIDEBAR_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WINDOW_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.WINDOW_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WORLD_SIZE
import icu.awkitsune.inkyrl.attributes.types.Fungus
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.world.Game
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.amethyst.api.entity.EntityType


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
        addFungi()

        world.addWorldEntity(EntityFactory.newFogOfWar())

        return Game.create(
            player = player,
            world = world
        )
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(
        atLevel: Int,
        atArea: Size = world.actualSize.to2DSize()
    ) : GameEntity<T> {
        world.addAtEmptyPosition(
            this,
            offset = Position3D.defaultPosition().withZ(atLevel),
            size = Size3D.from2DSize(atArea)
        )

        return this
    }

    private fun addPlayer() : GameEntity<Player> {
        return EntityFactory.newPlayer().addToWorld(
            atLevel = GameConfig.DUNGEON_LEVELS  - 1,
            atArea = world.visibleSize.to2DSize()
        )
    }
    private fun addFungi() = also {
        repeat(world.actualSize.zLength) { level ->
            repeat(FUNGI_PER_LEVEL) {
                EntityFactory.newFungus().addToWorld(level)
            }
        }
    }

    companion object {

        fun create() = GameBuilder(
            worldSize = WORLD_SIZE
        ).buildGame()
    }
}