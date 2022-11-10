package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.types.combatStats
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
import kotlin.math.max

object Attackable : BaseFacet<GameContext, Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message

        return if (attacker.isPlayer || target.isPlayer) {

            val damage = max(0, attacker.combatStats.attackValue - target.combatStats.defenseValue)
            val finalDamage = (Math.random() * damage).toInt() + 1

            target.combatStats.hp -= finalDamage
            logGameEvent("$attacker hits the $target for $finalDamage!", Attackable)

            if (target.hasNoHealthLeft()) {
                target.sendMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "different color ink"
                    )
                )
            }

            Consumed
        } else Pass
    }
}