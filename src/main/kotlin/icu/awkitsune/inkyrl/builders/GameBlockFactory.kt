package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.blocks.GameBlock

object GameBlockFactory {

    fun floor() = GameBlock(GameTileRepository.FLOOR)

    fun wall() = GameBlock.createWith(EntityFactory.newWall())

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())

}
