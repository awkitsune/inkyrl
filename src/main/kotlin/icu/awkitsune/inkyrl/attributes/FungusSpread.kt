package icu.awkitsune.inkyrl.attributes

import icu.awkitsune.inkyrl.GameConfig
import org.hexworks.amethyst.api.base.BaseAttribute

data class FungusSpread(
    var spreadCount: Int = 0,
    val maximumSpread: Int = GameConfig.MAXIMUM_FUNGUS_SPREAD
) : BaseAttribute()
