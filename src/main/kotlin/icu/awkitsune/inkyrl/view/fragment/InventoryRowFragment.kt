package icu.awkitsune.inkyrl.view.fragment

import icu.awkitsune.inkyrl.attributes.types.CombatItem
import icu.awkitsune.inkyrl.attributes.types.Food
import icu.awkitsune.inkyrl.attributes.types.iconTile
import icu.awkitsune.inkyrl.extensions.GameItem
import icu.awkitsune.inkyrl.extensions.whenTypeIs
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class InventoryRowFragment(width: Int, item: GameItem) : Fragment {

    val dropButton = Components.button()    // 1
            .withDecorations()
            .withText("Drop")
            .build()

    val eatButton = Components.button()     // 2
            .withDecorations()
            .withText("Eat")
            .build()

    val equipButton = Components.button()
            .withDecorations()
            .withText("Equip")
            .build()

    val examineButton = Components.button()
            .withDecorations()
            .withText("Examine")
            .build()

    override val root = Components.hbox()
            .withSpacing(1)
            .withSize(width, 1)
            .build().apply {
                addComponent(
                        Components.icon()
                                .withIcon(item.iconTile)
                )
                addComponent(
                        Components.label()
                                .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)
                                .withText(item.name)
                )
                addComponent(dropButton)
                addComponent(examineButton)
                item.whenTypeIs<Food> {     // 3
                    addComponent(eatButton)
                }
                item.whenTypeIs<CombatItem> {
                    addComponent(equipButton)
                }
            }
}
