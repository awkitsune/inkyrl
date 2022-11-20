package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.EnergyLevel
import icu.awkitsune.inkyrl.attributes.types.EnergyUser
import icu.awkitsune.inkyrl.attributes.types.energyLevel
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import icu.awkitsune.inkyrl.messages.Destroy
import icu.awkitsune.inkyrl.messages.Expend
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseActor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object EnergyExpender : BaseActor<GameContext, Expend>(Expend::class, EnergyLevel::class) {

    override suspend fun receive(message: Expend): Response {
        val (context, entity, energy) = message
        entity.energyLevel.currentEnergy -= energy              // 1
        checkStarvation(context, entity, entity.energyLevel)    // 2
        return Consumed
    }

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        entity.whenTypeIs<EnergyUser> {                         // 3
            entity.receiveMessage(
                Expend(                                         // 4
                    context = context,
                    source = it,
                    energy = 2
                )
            )
        }
        return true
    }

    private suspend fun checkStarvation(
        context: GameContext,
        entity: GameEntity<EntityType>,
        energyLevel: EnergyLevel
    ) {
        if (energyLevel.currentEnergy <= 0) {                   // 5
            entity.receiveMessage(
                Destroy(                                        // 6
                    context = context,
                    source = entity,
                    target = entity,
                    cause = "because of starvation"
                )
            )
        }
    }
}
