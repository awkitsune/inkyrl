package icu.awkitsune.inkyrl.view.fragment

import icu.awkitsune.inkyrl.attributes.DisplayableAttribute
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class PlayerStatsFragment(
    width: Int,
    player: GameEntity<Player>
) : Fragment {

    override val root = Components.vbox()
        .withSize(width, 30)                                                // 1
        .withSpacing(1)                                                    // 2
        .build().apply {
            addComponent(Components.header().withText("Player"))                // 3
            player.attributes.toList().filterIsInstance<DisplayableAttribute>()     // 4
                .forEach {
                    addComponent(it.toComponent(width))                             // 5
                }
        }
}
