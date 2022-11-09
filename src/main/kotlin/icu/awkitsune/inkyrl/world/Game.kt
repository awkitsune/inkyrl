package icu.awkitsune.inkyrl.world

import icu.awkitsune.inkyrl.GameConfig.GAME_AREA_SIZE
import icu.awkitsune.inkyrl.GameConfig.WORLD_SIZE
import icu.awkitsune.inkyrl.builders.WorldBuilder
import org.hexworks.zircon.api.data.Size3D

class Game(val world: World) {
    companion object {
        fun create(
            worldSize: Size3D = WORLD_SIZE,
            visibleSize: Size3D = GAME_AREA_SIZE
        ) = Game (
            WorldBuilder(worldSize)
                .makeCaves()
                .build(visibleSize)
        )
    }
}