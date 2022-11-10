package icu.awkitsune.inkyrl.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType (
    name = "player"
), Combatant

object Wall : BaseEntityType (
    name = "wall"
)

object Fungus : BaseEntityType (
    name = "fungus"
), Combatant