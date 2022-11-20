    package icu.awkitsune.inkyrl.systems

    import icu.awkitsune.inkyrl.attributes.CombatStats
    import icu.awkitsune.inkyrl.attributes.types.ExperienceGainer
    import icu.awkitsune.inkyrl.attributes.types.combatStats
    import icu.awkitsune.inkyrl.attributes.types.experience
    import icu.awkitsune.inkyrl.events.PlayerGainedLevel
    import icu.awkitsune.inkyrl.extensions.attackValue
    import icu.awkitsune.inkyrl.extensions.defenseValue
    import icu.awkitsune.inkyrl.extensions.isPlayer
    import icu.awkitsune.inkyrl.extensions.whenTypeIs
    import icu.awkitsune.inkyrl.functions.logGameEvent
    import icu.awkitsune.inkyrl.messages.EntityDestroyed
    import icu.awkitsune.inkyrl.world.GameContext
    import org.hexworks.amethyst.api.Consumed
    import org.hexworks.amethyst.api.Response
    import org.hexworks.amethyst.api.base.BaseFacet
    import org.hexworks.zircon.internal.Zircon
    import kotlin.math.min

    object ExperienceAccumulator : BaseFacet<GameContext, EntityDestroyed>(EntityDestroyed::class) {
        override suspend fun receive(message: EntityDestroyed): Response {
            val (_, defender, attacker) = message
            attacker.whenTypeIs<ExperienceGainer> { experienceGainer ->   // 1
                val xp = experienceGainer.experience
                val stats = experienceGainer.combatStats
                val defenderHp = defender.findAttribute(CombatStats::class).map { it.maxHp }.orElse(0)      // 2
                val amount = (defenderHp + defender.attackValue + defender.defenseValue) - xp.currentLevel * 2   // 3
                if (amount > 0) {
                    xp.currentXP += amount
                    while (xp.currentXP > Math.pow(xp.currentLevel.toDouble(), 1.5) * 20) {                      // 4
                        xp.currentLevel++
                        logGameEvent("$attacker advanced to level ${xp.currentLevel}.", ExperienceAccumulator)
                        stats.hpProperty.value = min(stats.hp + xp.currentLevel * 2, stats.maxHp)             // 5
                        if (attacker.isPlayer) {
                            Zircon.eventBus.publish(PlayerGainedLevel(ExperienceAccumulator))                   // 6
                        }
                    }
                }

            }
            return Consumed
        }
    }