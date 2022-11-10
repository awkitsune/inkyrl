package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.builders.GameColors.ACCENT_COLOR
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_FOREGROUND
import icu.awkitsune.inkyrl.builders.GameColors.WALL_BACKGROUND
import icu.awkitsune.inkyrl.builders.GameColors.WALL_FOREGROUND
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {
    val EMPTY: CharacterTile = Tile.empty()

    val FLOOR: CharacterTile = Tile.newBuilder()
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(FLOOR_FOREGROUND)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val WALL: CharacterTile = Tile.newBuilder()
        .withCharacter('#')
        .withForegroundColor(WALL_FOREGROUND)
        .withBackgroundColor(WALL_BACKGROUND)
        .buildCharacterTile()

    val PLAYER = Tile.newBuilder()
        .withCharacter('@')
        .withBackgroundColor(FLOOR_BACKGROUND)
        .withForegroundColor(ACCENT_COLOR)
        .buildCharacterTile()

    val FUNGUS = Tile.newBuilder()
        .withCharacter('f')
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .withForegroundColor(GameColors.FUNGUS_COLOR)
        .buildCharacterTile()
}