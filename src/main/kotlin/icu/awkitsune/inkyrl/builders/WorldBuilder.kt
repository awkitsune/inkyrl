package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.blocks.GameBlock
import icu.awkitsune.inkyrl.extensions.sameLevelNeighborsShuffled
import icu.awkitsune.inkyrl.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class WorldBuilder(private val worldSize: Size3D) {
    private val depth = worldSize.yLength
    private val width = worldSize.xLength
    private val height = worldSize.zLength
    private var blocks: MutableMap<Position3D, GameBlock> = mutableMapOf()
    
    fun makeCaves(): WorldBuilder {
        return randomizeTiles()
            .smooth(8)
            .connectLevels()
    }

    private fun connectLevels() = also {
        (height - 1).downTo(1).forEach(::connectRegionDown)
    }

    private fun smooth(iterations: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    blocks.whenPresent(neighbor) { block -> 
                        if (block.isEmptyFloor) {
                            floors++
                        } else rocks++
                    }
                }
                newBlocks[Position3D.create(x, y, z)] =
                    if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }

        return this
    }

    fun build(visibleSize: Size3D): World = World(blocks, visibleSize, worldSize)

    private fun randomizeTiles(): WorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = if (Math.random() < 0.5) {
                GameBlockFactory.floor()
            } else GameBlockFactory.wall()
        }
        
        return this
    }

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }

    private fun connectRegionDown(currentLevel: Int) {
        val posToConnect = generateRandomFloorPositionsOn(currentLevel)
            .first { pos ->
                blocks[pos].isEmptyFloor() && blocks[pos.below()].isEmptyFloor()
            }
        blocks[posToConnect] = GameBlockFactory.stairsDown()
        blocks[posToConnect.below()] = GameBlockFactory.stairsUp()
    }

    private fun generateRandomFloorPositionsOn(level: Int) = sequence {
        while (true) {
            var pos = Position3D.unknown()

            while (pos.isUnknown) {
                val candidate = Position3D.create(
                    x = Random.nextInt(width - 1),
                    y = Random.nextInt(depth - 1),
                    z = level
                )
                if (blocks[candidate].isEmptyFloor()) {
                    pos = candidate
                }
            }
            yield(pos)
        }
    }
}

private fun Position3D.below(): Position3D = copy(z = z - 1)

private fun GameBlock?.isEmptyFloor(): Boolean {
    return this?.isEmptyFloor ?: false
}

private fun MutableMap<Position3D, GameBlock>.whenPresent(pos: Position3D, fn: (GameBlock) -> Unit) {
    this[pos]?.let(fn)

}
