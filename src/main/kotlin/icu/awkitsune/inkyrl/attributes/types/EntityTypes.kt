package icu.awkitsune.inkyrl.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType(
        name = "Wall"
)

object Player : BaseEntityType(
        name = "Inkling"
), Combatant, ItemHolder, EnergyUser, EquipmentHolder, ExperienceGainer, SardiniumHolder

object Fungus : BaseEntityType(
        name = "Fungus"
), Combatant

object StairsDown : BaseEntityType(
        name = "Stairs down"
)

object StairsUp : BaseEntityType(
        name = "Stairs up"
)

object FOW : BaseEntityType(
        name = "Fog of War"
)

object Octarian : BaseEntityType(
        name = "Octarian trooper"
), Combatant, ItemHolder

object Sardinium : BaseEntityType(
        name = "Sardinium",
        description = "A small piece of Sardinium. It's value is great."
), Item

object OctoFood : BaseEntityType(
        name = "OctoFood",
        description = "Octarian soldier ration. It is edible, but not tasty."
), Food

object Dagger : BaseEntityType(
        name = "Rusty Dagger",
        description = "A small, rusty dagger made of some metal alloy."
), Weapon

object Sword : BaseEntityType(
        name = "Iron Sword",
        description = "A shiny sword made of iron. It is a two-hand weapon"
), Weapon

object Staff : BaseEntityType(
        name = "Wooden Staff",
        description = "A wooden staff made of birch. It has seen some use"
), Weapon

object LightArmor : BaseEntityType(
        name = "Leather Tunic",
        description = "A tunic made of rugged leather. It is very comfortable."
), Armor

object MediumArmor : BaseEntityType(
        name = "Chainmail",
        description = "A sturdy chainmail armor made of interlocking iron chains."
), Armor

object HeavyArmor : BaseEntityType(
        name = "Platemail",
        description = "A heavy and shiny platemail armor made of bronze."
), Armor

object Club : BaseEntityType(
        name = "Club",
        description = "A wooden club. It doesn't give you an edge over your opponent (haha)."
), Weapon

object Jacket : BaseEntityType(
        name = "Leather jacket",
        description = "Dirty and rugged jacket made of leather."
), Armor

object Octoling : BaseEntityType(
        name = "Octoling"
), Combatant, ItemHolder

object Exit : BaseEntityType(
        name = "Exit"
)
