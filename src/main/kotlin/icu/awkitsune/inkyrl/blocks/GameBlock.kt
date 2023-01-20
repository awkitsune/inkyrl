package icu.awkitsune.inkyrl.blocks

import icu.awkitsune.inkyrl.builders.GameColors.UNREVEALED_COLOR
import icu.awkitsune.inkyrl.builders.GameColors.UNREVEALED_COLOR_DARK
import icu.awkitsune.inkyrl.builders.GameColors.UNREVEALED_COLOR_LIGHT
import icu.awkitsune.inkyrl.builders.GameTileRepository
import icu.awkitsune.inkyrl.builders.GameTileRepository.FLOOR
import icu.awkitsune.inkyrl.builders.GameTileRepository.PLAYER
import icu.awkitsune.inkyrl.builders.GameTileRepository.UNREVEALED
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
import kotlin.random.Random

class GameBlock(
    private var defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf(),
) : BaseBlock<Tile>(
        emptyTile = Tile.empty(),
        tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)
) {

    val isFloor: Boolean
        get() = defaultTile == FLOOR

    val isWall: Boolean
        get() = defaultTile == WALL

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    val occupier: Maybe<GameEntity<EntityType>>
        get() = Maybe.ofNullable(currentEntities.firstOrNull { it.occupiesBlock })

    val isOccupied: Boolean
        get() = occupier.isPresent

    init {
        top = UNREVEALED.withBackgroundColor(if (Random.nextBoolean()) UNREVEALED_COLOR else if (Random.nextBoolean()) UNREVEALED_COLOR_LIGHT else UNREVEALED_COLOR_DARK)
        updateContent()
    }

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

    companion object {

        fun createWith(entity: GameEntity<EntityType>) = GameBlock(
                currentEntities = mutableListOf(entity)
        )
    }
}
