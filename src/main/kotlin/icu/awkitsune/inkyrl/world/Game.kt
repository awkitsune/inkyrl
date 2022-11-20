package icu.awkitsune.inkyrl.world

import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity

class Game(
    val world: World,
    val player: GameEntity<Player>
) {

    companion object {

        fun create(
            player: GameEntity<Player>,
            world: World
        ) = Game(
            world = world,
            player = player
        )
    }
}
