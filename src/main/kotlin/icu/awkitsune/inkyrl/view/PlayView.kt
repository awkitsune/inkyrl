package icu.awkitsune.inkyrl.view

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.builders.GameTileRepository
import icu.awkitsune.inkyrl.world.Game
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer

class PlayView(
    private val grid: TileGrid,
    private val game: Game = Game.create(),
    theme: ColorTheme = GameConfig.THEME
) : BaseView(grid, theme){

    init {
        val sidebar = Components.panel()
            .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT)
            .withDecorations(
                box(title = "Sidebar")
            )
            .build()

        val logArea = Components.logArea()
            .withDecorations(
                box(title = "Adventure log")
            )
            .withSize(
                GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH,
                GameConfig.LOG_HEIGHT
            )
            .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
            .build()

        val gameComponent = Components.panel()
            .withSize(game.world.visibleSize.to2DSize())
            .withComponentRenderer(
                GameAreaComponentRenderer(
                    gameArea = game.world,
                    projectionMode = ProjectionMode.TOP_DOWN.toProperty(),
                    fillerTile = GameTileRepository.FLOOR
                )
            )
            .withAlignmentWithin(screen, ComponentAlignment.TOP_RIGHT)
            .build()

        screen.addComponents(sidebar, logArea, gameComponent)
    }

}