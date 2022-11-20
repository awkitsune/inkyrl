package icu.awkitsune.inkyrl.systems

import icu.awkitsune.inkyrl.GameConfig
import icu.awkitsune.inkyrl.attributes.types.CombatItem
import icu.awkitsune.inkyrl.attributes.types.EnergyUser
import icu.awkitsune.inkyrl.attributes.types.EquipmentHolder
import icu.awkitsune.inkyrl.attributes.types.Food
import icu.awkitsune.inkyrl.attributes.types.equip
import icu.awkitsune.inkyrl.attributes.types.inventory
import icu.awkitsune.inkyrl.extensions.GameItem
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import icu.awkitsune.inkyrl.messages.DropItem
import icu.awkitsune.inkyrl.messages.Eat
import icu.awkitsune.inkyrl.messages.InspectInventory
import icu.awkitsune.inkyrl.view.dialog.ExamineDialog
import icu.awkitsune.inkyrl.view.fragment.InventoryFragment
import icu.awkitsune.inkyrl.world.GameContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.platform.Dispatchers
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object InventoryInspector : BaseFacet<GameContext, InspectInventory>(InspectInventory::class) {

    val DIALOG_SIZE = Size.create(40, 15)

    override suspend fun receive(message: InspectInventory): Response {
        val (context, itemHolder, position) = message

        val screen = context.screen

        val panel = Components.panel()
                .withSize(DIALOG_SIZE)
                .withDecorations(box(title = "Inventory"), shadow())
                .build()

        val fragment = InventoryFragment(
                inventory = itemHolder.inventory,
                width = DIALOG_SIZE.width - 3,
                onDrop = { item ->
                    CoroutineScope(Dispatchers.Single).launch {
                        itemHolder.receiveMessage(DropItem(context, itemHolder, item, position))
                    }
                },
                onEat = { item ->
                    CoroutineScope(Dispatchers.Single).launch {
                        itemHolder.whenTypeIs<EnergyUser> { eater ->
                            item.whenTypeIs<Food> { food ->
                                itemHolder.inventory.removeItem(food)
                                itemHolder.receiveMessage(Eat(context, eater, food))
                            }
                        }
                    }
                },
                onEquip = { item ->
                    var result = Maybe.empty<GameItem>()
                    itemHolder.whenTypeIs<EquipmentHolder> { equipmentHolder -> // 1
                        item.whenTypeIs<CombatItem> { combatItem ->             // 2
                            result = Maybe.of(equipmentHolder.equip(itemHolder.inventory, combatItem))  // 3
                        }
                    }
                    result  // 4
                },
                onExamine = { item ->
                    screen.openModal(ExamineDialog(screen, item))
                })

        panel.addFragment(fragment)

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
                .withParentSize(screen.size)
                .withComponent(panel)
                .withCenteredDialog(true)
                .build()

        panel.addComponent(Components.button()
                .withText("Close")
                .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_LEFT)
                .build().apply {
                    onActivated {
                        modal.close(EmptyModalResult)
                        Processed
                    }
                })

        modal.theme = GameConfig.THEME
        screen.openModal(modal)
        return Consumed
    }
}
