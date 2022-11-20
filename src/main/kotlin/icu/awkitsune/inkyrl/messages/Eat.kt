package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.attributes.types.EnergyUser
import icu.awkitsune.inkyrl.attributes.types.Food
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.world.GameContext

data class Eat(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    override val target: GameEntity<Food>
) : EntityAction<EnergyUser, Food>
