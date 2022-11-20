package icu.awkitsune.inkyrl.view

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class StartView constructor(
    private val grid: TileGrid
) : BaseView(grid, ColorThemes.arc()) {
    init {
        val msg = "                    InkyRL                    "
        val desc = "Descend into Alterna caves system and find as much Sardinium as you can!"
        val header = Components.textBox(contentWidth = msg.length)
            .addHeader(msg)
            .addNewLine()
            .addHeader(desc)
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .withDecorations(ComponentDecorations.side())
            .build()

        val startButton = Components.button()
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_CENTER)
            .withText("Let's go!")
            .withDecorations(box(), shadow())
            .build()

        startButton.onActivated {
            replaceWith(PlayView(grid))
        }

        screen.addComponents(header, startButton)
    }
}
