package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.attributes.*
import icu.awkitsune.inkyrl.attributes.flags.BlockOccupier
import icu.awkitsune.inkyrl.attributes.types.*
import icu.awkitsune.inkyrl.builders.GameTileRepository.FUNGUS
import icu.awkitsune.inkyrl.builders.GameTileRepository.PLAYER
import icu.awkitsune.inkyrl.builders.GameTileRepository.WALL
import icu.awkitsune.inkyrl.messages.Attack
import icu.awkitsune.inkyrl.messages.Dig
import icu.awkitsune.inkyrl.systems.*
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
            EntityPosition(),
            BlockOccupier,
            EntityTile(FUNGUS),
            fungusSpread,
            CombatStats.create(
                maxHp = 10,
                attackValue = 0,
                defenseValue = 0
            )
        )
        facets(Attackable, Destructible)
        behaviors(FungusGrowth)
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
            EntityPosition(),
            BlockOccupier,
            EntityTile(WALL),
            VisionBlocker
        )
        facets(Diggable)
        behaviors()
    }

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig::class, Attack::class),
            CombatStats.create(
                maxHp = 100,
                attackValue = 10,
                defenseValue = 5
            ),
            Vision(8)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover, StairClimber, StairDescender)
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_DOWN),
            EntityPosition()
        )
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(
            EntityTile(GameTileRepository.STAIRS_UP),
            EntityPosition()
        )
    }

    fun newFogOfWar() = newGameEntityOfType(FOW) {
        behaviors(FogOfWar)
    }
}