package icu.awkitsune.inkyrl

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size3D

object GameConfig {

    const val DUNGEON_LEVELS = 2

    val TILESET = CP437TilesetResources.yayo16x16()
    val THEME = ColorThemes.solarizedDarkCyan()

    const val SIDEBAR_WIDTH = 16
    const val LOG_HEIGHT = 8

    const val WINDOW_WIDTH = 60
    const val WINDOW_HEIGHT = 40

    val WORLD_SIZE = Size3D.create(
        xLength = WINDOW_WIDTH * 2,
        yLength = WINDOW_HEIGHT * 2,
        zLength = DUNGEON_LEVELS
    )
    val GAME_AREA_SIZE = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_HEIGHT,
        zLength = DUNGEON_LEVELS
    )

    fun buildAppConfig() = AppConfig.newBuilder()
        .withDefaultTileset(TILESET)
        .withSize(
            width = WINDOW_WIDTH,
            height = WINDOW_HEIGHT
        )
        .build()
}