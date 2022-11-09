package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.attributes.EntityActions
import icu.awkitsune.inkyrl.attributes.EntityPosition
import icu.awkitsune.inkyrl.attributes.EntityTile
import icu.awkitsune.inkyrl.attributes.flags.BlockOccupier
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.attributes.types.Wall
import icu.awkitsune.inkyrl.builders.GameTileRepository.PLAYER
import icu.awkitsune.inkyrl.builders.GameTileRepository.WALL
import icu.awkitsune.inkyrl.messages.Dig
import icu.awkitsune.inkyrl.systems.CameraMover
import icu.awkitsune.inkyrl.systems.Diggable
import icu.awkitsune.inkyrl.systems.InputReceiver
import icu.awkitsune.inkyrl.systems.Movable
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
            EntityPosition(),
            BlockOccupier,
            EntityTile(WALL)
        )
        facets(Diggable)
    }

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig::class)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover)
    }
}