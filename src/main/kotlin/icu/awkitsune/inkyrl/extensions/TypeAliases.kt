package icu.awkitsune.inkyrl.extensions

import icu.awkitsune.inkyrl.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

typealias AnyGameEntity = GameEntity<EntityType>

typealias GameEntity<T> = Entity<T, GameContext>

typealias GameMessage = Message<GameContext>