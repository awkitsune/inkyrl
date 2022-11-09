package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.messages.MoveTo
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPos = player.position

        if (uiEvent is KeyboardEvent) {
            val newPosition = when (uiEvent.code) {
                KeyCode.KEY_W -> currentPos.withRelativeY(-1)
                KeyCode.KEY_A -> currentPos.withRelativeX(-1)
                KeyCode.KEY_S -> currentPos.withRelativeY(1)
                KeyCode.KEY_D -> currentPos.withRelativeX(1)
                else -> {
                    currentPos
                }
            }
            player.receiveMessage(MoveTo(context, player, newPosition))
        }

        return true
    }
}