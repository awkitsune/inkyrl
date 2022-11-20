package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.NutritionalValue
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.tryToFindAttribute

interface Food : Item

val GameEntity<Food>.energy: Int
    get() = tryToFindAttribute(NutritionalValue::class).energy
