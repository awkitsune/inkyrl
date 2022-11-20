package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.combatStats
import icu.awkitsune.inkyrl.extensions.attackValue
import icu.awkitsune.inkyrl.extensions.defenseValue
import icu.awkitsune.inkyrl.extensions.hasNoHealthLeft
import icu.awkitsune.inkyrl.extensions.isPlayer
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.Attack
import icu.awkitsune.inkyrl.messages.Destroy
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Attackable : BaseFacet<GameContext, Attack>(Attack::class) {

    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message

        return if (attacker.isPlayer || target.isPlayer) { // 1

            val damage = Math.max(0, attacker.attackValue - target.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage

            logGameEvent("$attacker hits the $target for $finalDamage!", Attackable)

            if (target.hasNoHealthLeft()) {
                target.sendMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "after a last hit"
                    )
                )
            }
            Consumed
        } else Pass
    }
}
