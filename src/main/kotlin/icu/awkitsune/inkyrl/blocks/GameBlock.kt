package icu.awkitsune.inkyrl.blocks

import icu.awkitsune.inkyrl.builders.GameTileRepository
import icu.awkitsune.inkyrl.builders.GameTileRepository.EMPTY
import icu.awkitsune.inkyrl.builders.GameTileRepository.FLOOR
import icu.awkitsune.inkyrl.builders.GameTileRepository.PLAYER
import icu.awkitsune.inkyrl.builders.GameTileRepository.WALL
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.extensions.occupiesBlock
import icu.awkitsune.inkyrl.extensions.tile
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf()
) : BaseBlock<Tile> (
    emptyTile = EMPTY,
    tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)
){
    init {
        top = GameTileRepository.UNREVEALED
        updateContent()
    }
    companion object {
        fun createWith(entity: GameEntity<EntityType>) = GameBlock (
            currentEntities = mutableListOf(entity)
        )
    }

    val occupier: Maybe<GameEntity<EntityType>>
        get() = Maybe.ofNullable(currentEntities.firstOrNull { it.occupiesBlock })

    val isOccupied: Boolean
        get() = occupier.isPresent

    val isFloor: Boolean
        get() = content == FLOOR

    val isWall: Boolean
        get() = content == WALL

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
        updateContent()
    }

    private fun updateContent() {
        val entityTiles = currentEntities.map { it.tile }
        content = when {
            entityTiles.contains(PLAYER) -> PLAYER
            entityTiles.isNotEmpty() -> entityTiles.first()
            else -> defaultTile
        }
    }

}