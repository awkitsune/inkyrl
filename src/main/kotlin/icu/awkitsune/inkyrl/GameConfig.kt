package icu.awkitsune.inkyrl

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size3D

object GameConfig {

    // game
    const val DUNGEON_LEVELS = 10

    // look & feel
    val TILESET = CP437TilesetResources.yayo16x16()
    val THEME = ColorThemes.solarizedDarkCyan()
    const val SIDEBAR_WIDTH = 18
    const val LOG_AREA_HEIGHT = 6

    // sizing
    const val WINDOW_WIDTH = 70
    const val WINDOW_HEIGHT = 40

    // entities
    const val FUNGI_PER_LEVEL = 15
    const val MAXIMUM_FUNGUS_SPREAD = 30
    const val OCTARIANS_PER_LEVEL = 15
    const val SARDINIUM_PER_LEVEL = 20

    val WORLD_SIZE = Size3D.create(WINDOW_WIDTH * 2, WINDOW_HEIGHT * 2, DUNGEON_LEVELS)
    val GAME_COMPONENT_SIZE = Size3D.create(
            xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
            yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
            zLength = 1
    )

    const val OCTOLINGS_PER_LEVEL = 5

    fun buildAppConfig() = AppConfig.newBuilder()
            .withDefaultTileset(TILESET)
            .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
            .build()

}
