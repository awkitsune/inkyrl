package icu.awkitsune.inkyrl.extensions

import icu.awkitsune.inkyrl.attributes.EntityActions
import icu.awkitsune.inkyrl.attributes.EntityPosition
import icu.awkitsune.inkyrl.attributes.EntityTile
import icu.awkitsune.inkyrl.attributes.flags.BlockOccupier
import icu.awkitsune.inkyrl.attributes.types.Combatant
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.attributes.types.combatStats
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass

var AnyGameEntity.position
    get() = tryToFindAttribute(EntityPosition::class).position
    set(value) {
        findAttribute(EntityPosition::class).map {
            it.position = value
        }
    }

val AnyGameEntity.tile: Tile
    get() = this.tryToFindAttribute(EntityTile::class).tile

fun <T : Attribute> AnyGameEntity.tryToFindAttribute(kClass: KClass<T>): T = findAttribute(kClass).orElseThrow {
    NoSuchElementException("Entity '$this' has no property with type '${kClass.simpleName}'")
}

val AnyGameEntity.occupiesBlock: Boolean
    get() = findAttribute(BlockOccupier::class).isPresent

suspend fun AnyGameEntity.tryActionsOn(context: GameContext, target: AnyGameEntity) : Response {
    var result: Response = Pass

    findAttributeOrNull(EntityActions::class)?.let {
        it.createActionsFor(context, this, target).forEach { action ->
            if (target.receiveMessage(action) is Consumed) {
                result = Consumed
                return@forEach
            }
        }
    }

    return result
}

val AnyGameEntity.isPlayer: Boolean
    get() = this.type == Player

fun GameEntity<Combatant>.hasNoHealthLeft(): Boolean = combatStats.hp <=0
