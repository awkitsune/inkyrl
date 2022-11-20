package icu.awkitsune.inkyrl.world

import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.amethyst.api.Context
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

data class GameContext(
    val world: World,           // 1
    val screen: Screen,         // 2
    val uiEvent: UIEvent,       // 3
    val player: GameEntity<Player>
) : Context                     // 4
