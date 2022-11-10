package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.messages.MoveTo
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.messages.MoveDown
import icu.awkitsune.inkyrl.messages.MoveUp
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(this::class)
    override suspend fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPos = player.position

        if (uiEvent is KeyboardEvent) {
            val newPosition = when (uiEvent.code) {
                KeyCode.KEY_W -> player.moveTo(currentPos.withRelativeY(-1), context)
                KeyCode.KEY_A -> player.moveTo(currentPos.withRelativeX(-1), context)
                KeyCode.KEY_S -> player.moveTo(currentPos.withRelativeY(1), context)
                KeyCode.KEY_D -> player.moveTo(currentPos.withRelativeX(1), context)
                KeyCode.KEY_R -> player.moveUp(context)
                KeyCode.KEY_F -> player.moveDown(context)
                else -> {
                    logger.debug("UI Event ($uiEvent) does not have a corresponding command, it is ignored")
                }
            }
        }

        return true
    }

    private suspend fun GameEntity<Player>.moveTo(position: Position3D, context: GameContext) { // 2
        receiveMessage(MoveTo(context, this, position))
    }

    private suspend fun GameEntity<Player>.moveUp(context: GameContext) {   // 3
        receiveMessage(MoveUp(context, this))
    }

    private suspend fun GameEntity<Player>.moveDown(context: GameContext) {
        receiveMessage(MoveDown(context, this))
    }
}