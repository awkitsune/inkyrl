package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.Inventory
import icu.awkitsune.inkyrl.extensions.GameItem
import icu.awkitsune.inkyrl.extensions.GameItemHolder
import org.hexworks.amethyst.api.entity.EntityType

interface ItemHolder : EntityType

fun GameItemHolder.addItem(item: GameItem) = inventory.addItem(item)

fun GameItemHolder.removeItem(item: GameItem) = inventory.removeItem(item)

val GameItemHolder.inventory: Inventory
    get() = findAttribute(Inventory::class).get()
