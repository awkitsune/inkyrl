package icu.awkitsune.inkyrl.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType (
    name = "Player"
), Combatant

object Wall : BaseEntityType (
    name = "Wall"
)

object Fungus : BaseEntityType (
    name = "Fungus"
), Combatant

object StairsDown : BaseEntityType (
    name = "stairs down"
)

object StairsUp : BaseEntityType (
    name = "stairs up"
)

object FOW: BaseEntityType (
    name = "Fog of War"
)