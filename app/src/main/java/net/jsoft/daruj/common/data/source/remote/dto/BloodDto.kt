package net.jsoft.daruj.common.data.source.remote.dto

import androidx.annotation.Keep
import net.jsoft.daruj.common.domain.model.Blood

@Keep
class BloodDto(
    var type: Blood.Type? = null,
    var rh: Blood.RH? = null
) {
    fun getModel() = Blood(
        type = type!!,
        rh = rh!!
    )
}