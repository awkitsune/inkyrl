package icu.awkitsune.inkyrl.messages

import icu.awkitsune.inkyrl.attributes.types.Combatant
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.world.GameContext

data class Attack(
    override val context: GameContext,
    override val source: GameEntity<Combatant>,
    override val target: GameEntity<Combatant>
) : EntityAction<Combatant, Combatant>
