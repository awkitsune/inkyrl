package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.EnergyLevel
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface EnergyUser : EntityType

val GameEntity<EnergyUser>.energyLevel: EnergyLevel
    get() = findAttribute(EnergyLevel::class).get()
