package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.attributes.SardiniumCounter
import icu.awkitsune.inkyrl.attributes.types.Sardinium
import icu.awkitsune.inkyrl.attributes.types.SardiniumHolder
import icu.awkitsune.inkyrl.attributes.types.sardiniumCounter
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import icu.awkitsune.inkyrl.functions.logGameEvent
import icu.awkitsune.inkyrl.messages.PickItemUp
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object SardiniumGatherer : BaseFacet<GameContext, PickItemUp>(PickItemUp::class, SardiniumCounter::class) {

    override suspend fun receive(message: PickItemUp): Response {
        val (context, source, position) = message
        var response: Response = Pass
        val world = context.world
        world.findTopItem(position).map { item ->               // 1
            source.whenTypeIs<SardiniumHolder> { zirconHolder ->   // 2
                if (item.type == Sardinium) {                      // 3
                    zirconHolder.sardiniumCounter.sardiniumCount++    // 4
                    world.removeEntity(item)                    // 5
                    logGameEvent("$zirconHolder picked up a Sardinium!", SardiniumGatherer)
                    response = Consumed
                }
            }
        }
        return response
    }


}