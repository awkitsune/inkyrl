package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.SardiniumCounter
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface SardiniumHolder : EntityType

val GameEntity<SardiniumHolder>.sardiniumCounter: SardiniumCounter
    get() = findAttribute(SardiniumCounter::class).get()