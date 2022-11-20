package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.Equipment
import icu.awkitsune.inkyrl.attributes.Inventory
import icu.awkitsune.inkyrl.extensions.GameCombatItem
import icu.awkitsune.inkyrl.extensions.GameEquipmentHolder
import org.hexworks.amethyst.api.entity.EntityType

interface EquipmentHolder : EntityType

fun GameEquipmentHolder.equip(inventory: Inventory, item: GameCombatItem): GameCombatItem {
    return equipment.equip(inventory, item)
}

val GameEquipmentHolder.equipment: Equipment
    get() = findAttribute(Equipment::class).get()