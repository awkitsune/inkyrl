package icu.awkitsune.inkyrl.extensions

import icu.awkitsune.inkyrl.attributes.types.CombatItem
import icu.awkitsune.inkyrl.attributes.types.EquipmentHolder
import icu.awkitsune.inkyrl.attributes.types.Item
import icu.awkitsune.inkyrl.attributes.types.ItemHolder
import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias AnyGameEntity = GameEntity<EntityType>

typealias GameEntity<T> = Entity<T, GameContext>

typealias GameMessage = Message<GameContext>

typealias GameItem = GameEntity<Item>

typealias GameItemHolder = GameEntity<ItemHolder>

typealias GameCombatItem = GameEntity<CombatItem>

typealias GameEquipmentHolder = GameEntity<EquipmentHolder>