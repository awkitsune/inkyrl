package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.blocks.GameBlock
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND_DARK
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND_LIGHT
import kotlin.random.Random

object GameBlockFactory {

    fun floor() = GameBlock(GameTileRepository.FLOOR.withBackgroundColor(if (Random.nextBoolean()) FLOOR_BACKGROUND else if (Random.nextBoolean()) FLOOR_BACKGROUND_LIGHT else FLOOR_BACKGROUND_DARK))

    fun wall() = GameBlock.createWith(EntityFactory.newWall())

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())

}
