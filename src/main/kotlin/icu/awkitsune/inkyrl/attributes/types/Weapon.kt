package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.ItemCombatStats
import icu.awkitsune.inkyrl.extensions.GameEntity

interface Weapon : CombatItem

val GameEntity<Weapon>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val GameEntity<Weapon>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue