package icu.awkitsune.inkyrl.attributes.types

import icu.awkitsune.inkyrl.attributes.CombatStats
import icu.awkitsune.inkyrl.attributes.Experience
import icu.awkitsune.inkyrl.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience
    get() = findAttribute(Experience::class).get()

val GameEntity<ExperienceGainer>.combatStats: CombatStats
    get() = findAttribute(CombatStats::class).get()