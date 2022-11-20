package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.addItem
import icu.awkitsune.inkyrl.extensions.isPlayer
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.PickItemUp
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemPicker : BaseFacet<GameContext, PickItemUp>(PickItemUp::class) {

    override suspend fun receive(message: PickItemUp): Response {
        val (context, itemHolder, position) = message
        val world = context.world
        world.findTopItem(position).map { item ->                                   // 1
            if (itemHolder.addItem(item)) {                                         // 2
                world.removeEntity(item)                                            // 3
                val subject = if (itemHolder.isPlayer) "You" else "$itemHolder" // 4
                val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
                logGameEvent("$subject $verb the $item.", ItemPicker)
            }
        }
        return Consumed
    }



}
