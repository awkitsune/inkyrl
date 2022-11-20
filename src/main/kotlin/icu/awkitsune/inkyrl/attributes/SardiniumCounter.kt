package icu.awkitsune.inkyrl.attributes

import icu.awkitsune.inkyrl.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

data class SardiniumCounter(
        private val sardiniumCountProperty: Property<Int> = createPropertyFrom(0)
) : BaseAttribute(), DisplayableAttribute {

    var sardiniumCount: Int by sardiniumCountProperty.asDelegate()

    override fun toComponent(width: Int): Component {
        val sardiniumProp = createPropertyFrom("Zircons: ")
                .bindPlusWith(sardiniumCountProperty.toStringProperty())
        return Components.header()
                .withText(sardiniumProp.value)
                .withSize(width, 1)
                .build().apply {
                    textProperty.updateFrom(sardiniumProp)
                }
    }
}