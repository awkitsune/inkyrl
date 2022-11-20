package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.ItemHolder
import icu.awkitsune.inkyrl.attributes.types.inventory
import icu.awkitsune.inkyrl.extensions.position
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import icu.awkitsune.inkyrl.messages.Destroy
import icu.awkitsune.inkyrl.messages.DropItem
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object LootDropper : BaseFacet<GameContext, Destroy>(Destroy::class) {

    override suspend fun receive(message: Destroy): Response {
        val (context, _, target) = message              // 1
        target.whenTypeIs<ItemHolder> { entity ->       // 2
            entity.inventory.items.forEach { item ->
                entity.receiveMessage(DropItem(context, entity, item, entity.position))     // 3
            }
        }
        return Pass                                     // 4
    }
}
