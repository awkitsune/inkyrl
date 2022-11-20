package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.attributes.types.EnergyUser
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.GameMessage
import icu.awkitsune.inkyrl.world.GameContext

data class Expend(
    override val context: GameContext,
    override val source: GameEntity<EnergyUser>,
    val energy: Int
) : GameMessage
