package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.ItemCombatStats
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.entity.Entity

interface Armor : CombatItem

val Entity<Armor, GameContext>.attackValue: Int
    get() = findAttribute(ItemCombatStats::class).get().attackValue

val Entity<Armor, GameContext>.defenseValue: Int
    get() = findAttribute(ItemCombatStats::class).get().defenseValue