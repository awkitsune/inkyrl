package icu.awkitsune.inkyrl.builders

import icu.awkitsune.inkyrl.attributes.CombatStats
import icu.awkitsune.inkyrl.attributes.EnergyLevel
import icu.awkitsune.inkyrl.attributes.EntityActions
import icu.awkitsune.inkyrl.attributes.EntityPosition
import icu.awkitsune.inkyrl.attributes.EntityTile
import icu.awkitsune.inkyrl.attributes.Equipment
import icu.awkitsune.inkyrl.attributes.Experience
import icu.awkitsune.inkyrl.attributes.FungusSpread
import icu.awkitsune.inkyrl.attributes.Inventory
import icu.awkitsune.inkyrl.attributes.ItemCombatStats
import icu.awkitsune.inkyrl.attributes.ItemIcon
import icu.awkitsune.inkyrl.attributes.NutritionalValue
import icu.awkitsune.inkyrl.attributes.Vision
import icu.awkitsune.inkyrl.attributes.VisionBlocker
import icu.awkitsune.inkyrl.attributes.SardiniumCounter
import icu.awkitsune.inkyrl.attributes.flags.BlockOccupier
import icu.awkitsune.inkyrl.attributes.types.Armor
import icu.awkitsune.inkyrl.attributes.types.Octarian
import icu.awkitsune.inkyrl.attributes.types.OctoFood
import icu.awkitsune.inkyrl.attributes.types.Club
import icu.awkitsune.inkyrl.attributes.types.Dagger
import icu.awkitsune.inkyrl.attributes.types.Exit
import icu.awkitsune.inkyrl.attributes.types.FOW
import icu.awkitsune.inkyrl.attributes.types.Fungus
import icu.awkitsune.inkyrl.attributes.types.HeavyArmor
import icu.awkitsune.inkyrl.attributes.types.Jacket
import icu.awkitsune.inkyrl.attributes.types.LightArmor
import icu.awkitsune.inkyrl.attributes.types.MediumArmor
import icu.awkitsune.inkyrl.attributes.types.Player
import icu.awkitsune.inkyrl.attributes.types.Staff
import icu.awkitsune.inkyrl.attributes.types.StairsDown
import icu.awkitsune.inkyrl.attributes.types.StairsUp
import icu.awkitsune.inkyrl.attributes.types.Sword
import icu.awkitsune.inkyrl.attributes.types.Wall
import icu.awkitsune.inkyrl.attributes.types.Weapon
import icu.awkitsune.inkyrl.attributes.types.Sardinium
import icu.awkitsune.inkyrl.attributes.types.Octoling
import icu.awkitsune.inkyrl.extensions.GameEntity
import icu.awkitsune.inkyrl.messages.Attack
import icu.awkitsune.inkyrl.messages.Dig
import icu.awkitsune.inkyrl.systems.Attackable
import icu.awkitsune.inkyrl.systems.CameraMover
import icu.awkitsune.inkyrl.systems.Destructible
import icu.awkitsune.inkyrl.systems.DigestiveSystem
import icu.awkitsune.inkyrl.systems.Diggable
import icu.awkitsune.inkyrl.systems.EnergyExpender
import icu.awkitsune.inkyrl.systems.ExperienceAccumulator
import icu.awkitsune.inkyrl.systems.FogOfWar
import icu.awkitsune.inkyrl.systems.FungusGrowth
import icu.awkitsune.inkyrl.systems.HunterSeeker
import icu.awkitsune.inkyrl.systems.InputReceiver
import icu.awkitsune.inkyrl.systems.InventoryInspector
import icu.awkitsune.inkyrl.systems.ItemDropper
import icu.awkitsune.inkyrl.systems.ItemPicker
import icu.awkitsune.inkyrl.systems.LootDropper
import icu.awkitsune.inkyrl.systems.Movable
import icu.awkitsune.inkyrl.systems.StairClimber
import icu.awkitsune.inkyrl.systems.StairDescender
import icu.awkitsune.inkyrl.systems.Wanderer
import icu.awkitsune.inkyrl.systems.SardiniumGatherer
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.data.Tile
import kotlin.random.Random

fun <T : EntityType> newGameEntityOfType(
        type: T,
        init: EntityBuilder<T, GameContext>.() -> Unit
) = newEntityOfType(type, init)

object EntityFactory {

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(
                Vision(9),
                EntityPosition(),
                BlockOccupier,
                CombatStats.create(
                        maxHp = 100,
                        attackValue = 10,
                        defenseValue = 5
                ),
                EntityTile(GameTileRepository.PLAYER),
                EntityActions(Dig::class, Attack::class),
                Inventory(10),
                EnergyLevel(1000, 1000),
                Equipment(
                        initialWeapon = newClub(),
                        initialArmor = newJacket()
                ),
                Experience(),
                SardiniumCounter()
        )
        behaviors(InputReceiver, EnergyExpender)
        facets(
                Movable,
                CameraMover,
                StairClimber,
                StairDescender,
                Attackable,
                Destructible,
                SardiniumGatherer,
                ItemPicker,
                InventoryInspector,
                ItemDropper,
                EnergyExpender,
                DigestiveSystem,
                ExperienceAccumulator
        )
    }

    fun newWall() = newGameEntityOfType(Wall) {
        attributes(
                EntityPosition(),
                BlockOccupier,
                EntityTile(GameTileRepository.WALL),
                VisionBlocker
        )
        facets(Diggable)
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.FUNGUS),
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

    fun newOctarian() = newGameEntityOfType(Octarian) {
        attributes(
                BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.OCTARIAN),
                CombatStats.create(
                        maxHp = 5,
                        attackValue = 2,
                        defenseValue = 1
                ),
                EntityActions(Attack::class),
                Inventory(1).apply {
                    addItem(newOctoFood())   // 1
                }
        )
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(Wanderer)
    }

    fun newSardinium() = newGameEntityOfType(Sardinium) {
        attributes(
                ItemIcon(
                        Tile.newBuilder()
                                .withName("white gem")
                                .withTileset(GraphicalTilesetResources.nethack16x16())
                                .buildGraphicalTile()
                ),
                EntityPosition(),
                EntityTile(GameTileRepository.SARDINIUM)
        )
    }

    fun newOctoFood() = newGameEntityOfType(OctoFood) {
        attributes(
                ItemIcon(
                        Tile.newBuilder()
                                .withName("Meatball")           // 1
                                .withTileset(GraphicalTilesetResources.nethack16x16())
                                .buildGraphicalTile()
                ),
                NutritionalValue(750),              // 2
                EntityPosition(),
                EntityTile(GameTileRepository.OCTO_FOOD)
        )
    }

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("Dagger")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.DAGGER)
        )
    }

    fun newSword() = newGameEntityOfType(Sword) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("Short sword")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 6,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.SWORD)
        )
    }

    fun newStaff() = newGameEntityOfType(Staff) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("staff")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        defenseValue = 2,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.STAFF)
        )
    }

    fun newLightArmor() = newGameEntityOfType(LightArmor) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("Leather armor")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 2,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.LIGHT_ARMOR)
        )
    }

    fun newMediumArmor() = newGameEntityOfType(MediumArmor) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("Chain mail")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 3,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.MEDIUM_ARMOR)
        )
    }

    fun newHeavyArmor() = newGameEntityOfType(HeavyArmor) {
        attributes(
            ItemIcon(Tile.newBuilder()
                .withName("Plate mail")
                .withTileset(GraphicalTilesetResources.nethack16x16())
                .buildGraphicalTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 4,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.HEAVY_ARMOR)
        )
    }

    fun newClub() = newGameEntityOfType(Club) {
        attributes(
            ItemCombatStats(combatItemType = "Weapon"),
                EntityTile(GameTileRepository.CLUB),
                EntityPosition(),
                ItemIcon(Tile.newBuilder()
                        .withName("Club")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile())
        )
    }

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(
            ItemCombatStats(combatItemType = "Armor"),
                EntityTile(GameTileRepository.JACKET),
                EntityPosition(),
                ItemIcon(Tile.newBuilder()
                        .withName("Leather jacket")
                        .withTileset(GraphicalTilesetResources.nethack16x16())
                        .buildGraphicalTile())
        )
    }

    fun newRandomWeapon(): GameEntity<Weapon> = when (Random.nextInt(3)) {
        0 -> newDagger()
        1 -> newSword()
        else -> newStaff()
    }

    fun newRandomArmor(): GameEntity<Armor> = when (Random.nextInt(3)) {
        0 -> newLightArmor()
        1 -> newMediumArmor()
        else -> newHeavyArmor()
    }

    fun newOctoling() = newGameEntityOfType(Octoling) {
        attributes(
            BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.OCTOLING),
                Vision(10),
                CombatStats.create(
                        maxHp = 25,
                        attackValue = 8,
                        defenseValue = 4),
                Inventory(2).apply {
                    addItem(newRandomWeapon())
                    addItem(newRandomArmor())
                },
                EntityActions(Attack::class)
        )
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(HunterSeeker or Wanderer)
    }

    fun newExit() = newGameEntityOfType(Exit) {
        attributes(
            EntityTile(GameTileRepository.EXIT),
                EntityPosition()
        )
    }
}
