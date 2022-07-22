package net.jsoft.daruj.common.data.dto

import net.jsoft.daruj.common.domain.model.Blood

class BloodDto(
    var type: Blood.Type? = null,
    var rh: Blood.RH? = null
) {
    fun getModel() = Blood(type!!, rh!!)
}