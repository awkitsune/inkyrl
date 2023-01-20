package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.builders.GameColors.ACCENT_COLOR
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND_DARK
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_BACKGROUND_LIGHT
import icu.awkitsune.inkyrl.builders.GameColors.FLOOR_FOREGROUND
import icu.awkitsune.inkyrl.builders.GameColors.UNREVEALED_COLOR
import icu.awkitsune.inkyrl.builders.GameColors.WALL_BACKGROUND
import icu.awkitsune.inkyrl.builders.GameColors.WALL_FOREGROUND
import icu.awkitsune.inkyrl.builders.GameColors.WALL_FOREGROUND_DARK
import icu.awkitsune.inkyrl.builders.GameColors.WALL_FOREGROUND_LIGHT
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols
import kotlin.random.Random

object GameTileRepository {
    val EMPTY: CharacterTile = Tile.empty()

    val FLOOR: CharacterTile = Tile.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(FLOOR_FOREGROUND)
            .withBackgroundColor(FLOOR_BACKGROUND)
            .buildCharacterTile()

    val WALL: CharacterTile = Tile.newBuilder()
            .withCharacter('█')
            //.withForegroundColor(WALL_FOREGROUND)
            .withForegroundColor(WALL_FOREGROUND)
            .withBackgroundColor(WALL_BACKGROUND)
            .buildCharacterTile()

    val STAIRS_UP = Tile.newBuilder()
            .withCharacter('╔')
            .withForegroundColor(GameColors.ACCENT_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val STAIRS_DOWN = Tile.newBuilder()
            .withCharacter('╝')
            .withForegroundColor(GameColors.ACCENT_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val UNREVEALED = Tile.newBuilder()
            .withCharacter(' ')
            .withBackgroundColor(UNREVEALED_COLOR)
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

    val OCTARIAN = Tile.newBuilder()
            .withCharacter('t')
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .withForegroundColor(GameColors.OCTARIAN_COLOR)
            .buildCharacterTile()

    val SARDINIUM = Tile.newBuilder()
            .withCharacter('§')
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .withForegroundColor(GameColors.SARDINIUM_COLOR)
            .buildCharacterTile()

    val OCTO_FOOD = Tile.newBuilder()
            .withCharacter('m')
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .withForegroundColor(GameColors.OCTO_FOOD_COLOR)
            .buildCharacterTile()

    val CLUB = Tile.newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.GRAY)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val DAGGER = Tile.newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val SWORD = Tile.newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val STAFF = Tile.newBuilder()
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.YELLOW)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val JACKET = Tile.newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GRAY)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val LIGHT_ARMOR = Tile.newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GREEN)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val MEDIUM_ARMOR = Tile.newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val HEAVY_ARMOR = Tile.newBuilder()
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val OCTOLING = Tile.newBuilder()
            .withCharacter('o')
            .withForegroundColor(GameColors.OCTOLING_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val EXIT = Tile.newBuilder()
            .withCharacter('+')
            .withForegroundColor(GameColors.ACCENT_COLOR)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()
}
