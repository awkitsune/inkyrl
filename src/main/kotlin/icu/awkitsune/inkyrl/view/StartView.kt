package icu.awkitsune.inkyrl.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class StartView(
    private val grid: TileGrid
) : BaseView(grid, ColorThemes.solarizedDarkCyan()){

    init {
        val msg = "Welcome to Alterna"

        val header = Components.textBox(msg.length)
            .addHeader(msg)
            .addNewLine()
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .build()

        val startButton = Components.button()
            .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_CENTER)
            .withText("Start")
            .withDecorations(box(), shadow())
            .build()

        startButton.onActivated {
            replaceWith(PlayView(grid))
        }

        screen.addComponents(header, startButton)
    }

}