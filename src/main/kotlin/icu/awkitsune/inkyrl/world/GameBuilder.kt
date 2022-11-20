package icu.awkitsune.inkyrl.world

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.GameConfig.OCTARIANS_PER_LEVEL
import icu.awkitsune.inkyrl.GameConfig.FUNGI_PER_LEVEL
import icu.awkitsune.inkyrl.GameConfig.LOG_AREA_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.SIDEBAR_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WINDOW_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.WINDOW_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WORLD_SIZE
import icu.awkitsune.inkyrl.GameConfig.OCTOLINGS_PER_LEVEL
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.builders.EntityFactory
import icu.awkitsune.inkyrl.builders.WorldBuilder
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D

class GameBuilder(val worldSize: Size3D) {

    private val visibleSize = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
        zLength = 1
    )

    val world = WorldBuilder(worldSize)
        .makeCaves()
        .build(visibleSize = visibleSize)

    fun buildGame(): Game {

        prepareWorld()

        val player = addPlayer()
        addFungi()
        addOctarians()
        addSardiniums()
        addOctolings()
        addExit()

        world.addWorldEntity(EntityFactory.newFogOfWar())

        return Game.create(
            player = player,
            world = world
        )
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        return EntityFactory.newPlayer().addToWorld(
            atLevel = GameConfig.DUNGEON_LEVELS - 1,
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

    private fun addOctarians() = also {
        repeat(world.actualSize.zLength) { level ->
            repeat(OCTARIANS_PER_LEVEL) {
                EntityFactory.newOctarian().addToWorld(level)
            }
        }
    }

    private fun addSardiniums() = also {
        repeat(world.actualSize.zLength) { level ->
            repeat(GameConfig.SARDINIUM_PER_LEVEL) {
                EntityFactory.newSardinium().addToWorld(level)
            }
        }
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(             // 1
        atLevel: Int,                                                  // 2
        atArea: Size = world.actualSize.to2DSize()
    ): GameEntity<T> {   // 3
        world.addAtEmptyPosition(
            this,
            offset = Position3D.defaultPosition().withZ(atLevel),      // 4
            size = Size3D.from2DSize(atArea)
        )                          // 5
        return this
    }

    private fun addOctolings() = also {
        repeat(world.actualSize.zLength) { level ->
            repeat(OCTOLINGS_PER_LEVEL) {
                EntityFactory.newOctoling().addToWorld(level)
            }
        }
    }

    private fun addExit() = also {
        EntityFactory.newExit().addToWorld(0)
    }

    companion object {

        fun create() = GameBuilder(
            worldSize = WORLD_SIZE
        ).buildGame()
    }
}
