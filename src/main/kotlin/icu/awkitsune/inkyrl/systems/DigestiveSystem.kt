package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.EnergyLevel
import icu.awkitsune.inkyrl.attributes.types.energy
import icu.awkitsune.inkyrl.attributes.types.energyLevel
import icu.awkitsune.inkyrl.extensions.isPlayer
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.Eat
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object DigestiveSystem : BaseFacet<GameContext, Eat>(Eat::class, EnergyLevel::class) {
    override suspend fun receive(message: Eat): Response {
        val (_, entity, food) = message
        entity.energyLevel.currentEnergy += food.energy
        val verb = if (entity.isPlayer) {
            "You eat"
        } else "$entity eats"
        logGameEvent("$verb the $food.", DigestiveSystem)
        return Consumed
    }
}
