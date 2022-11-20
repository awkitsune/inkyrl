package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.removeItem
import icu.awkitsune.inkyrl.extensions.isPlayer
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.DropItem
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemDropper : BaseFacet<GameContext, DropItem>(DropItem::class) {
    override suspend fun receive(message: DropItem): Response {
        val (context, itemHolder, item, position) = message // 1
        if (itemHolder.removeItem(item)) {                  // 2
            context.world.addEntity(item, position)         // 3
            val subject = if (itemHolder.isPlayer) "You" else "$itemHolder"
            val verb = if (itemHolder.isPlayer) "drop" else "drops"
            logGameEvent("$subject $verb the $item.", ItemDropper)
        }
        return Consumed
    }
}

