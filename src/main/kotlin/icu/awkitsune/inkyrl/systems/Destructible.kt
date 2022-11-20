package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.events.PlayerDied
import icu.awkitsune.inkyrl.extensions.isPlayer
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.Destroy
import icu.awkitsune.inkyrl.messages.EntityDestroyed
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        if (target.isPlayer) {
            Zircon.eventBus.publish(PlayerDied("You died $cause", Destructible))
        }
        logGameEvent("$target dies $cause.", Destructible)
        return Consumed
    }
}
