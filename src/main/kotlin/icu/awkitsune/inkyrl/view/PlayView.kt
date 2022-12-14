package icu.awkitsune.inkyrl.view

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.GameConfig.LOG_AREA_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.SIDEBAR_WIDTH
import icu.awkitsune.inkyrl.GameConfig.WINDOW_HEIGHT
import icu.awkitsune.inkyrl.GameConfig.WINDOW_WIDTH
import icu.awkitsune.inkyrl.builders.GameTileRepository
import icu.awkitsune.inkyrl.events.GameLogEvent
import icu.awkitsune.inkyrl.events.PlayerDied
import icu.awkitsune.inkyrl.events.PlayerGainedLevel
import icu.awkitsune.inkyrl.events.PlayerWonTheGame
import icu.awkitsune.inkyrl.view.dialog.LevelUpDialog
import icu.awkitsune.inkyrl.view.fragment.PlayerStatsFragment
import icu.awkitsune.inkyrl.world.Game
import icu.awkitsune.inkyrl.world.GameBuilder
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.DisposeSubscription
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer

class PlayView(
    private val grid: TileGrid,
    private val game: Game = GameBuilder.create(),
    theme: ColorTheme = GameConfig.THEME
) : BaseView(grid, theme) {

    init {
        val sidebar = Components.panel()
                .withSize(SIDEBAR_WIDTH, WINDOW_HEIGHT)
                .withDecorations(box())
                .build()

        sidebar.addFragment(
                PlayerStatsFragment(
                        width = sidebar.contentSize.width,
                        player = game.player
                )
        )

        val logArea = Components.logArea()
                .withDecorations(box(title = "Log"))
                .withSize(WINDOW_WIDTH - SIDEBAR_WIDTH, LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, BOTTOM_RIGHT)
                .build()

        val gameComponent = Components.panel()
                .withSize(game.world.visibleSize.to2DSize())
                .withComponentRenderer(
                        GameAreaComponentRenderer(
                                gameArea = game.world,
                                projectionMode = TOP_DOWN.toProperty(),
                                fillerTile = GameTileRepository.FLOOR
                        )
                )
                .withAlignmentWithin(screen, TOP_RIGHT)
                .build()

        screen.addComponents(sidebar, logArea, gameComponent)
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event, _ ->
            game.world.update(screen, event, game)
            Processed
        }

        Zircon.eventBus.subscribeTo<GameLogEvent> { (text) ->
            logArea.addParagraph(
                    paragraph = text,
                    withNewLine = false,
                    withTypingEffectSpeedInMs = 10
            )
            KeepSubscription
        }

        Zircon.eventBus.subscribeTo<PlayerGainedLevel> {
            screen.openModal(LevelUpDialog(screen, game.player))
            KeepSubscription
        }

        Zircon.eventBus.subscribeTo<PlayerWonTheGame> {
            replaceWith(WinView(grid, it.sardiniums))
            DisposeSubscription
        }

        Zircon.eventBus.subscribeTo<PlayerDied> {
            replaceWith(LoseView(grid, it.cause))
            DisposeSubscription
        }

        game.world.update(
                screen, KeyboardEvent(
                type = KeyboardEventType.KEY_TYPED,
                key = "",
                code = KeyCode.DEAD_GRAVE
        ), game
        )
    }

}
