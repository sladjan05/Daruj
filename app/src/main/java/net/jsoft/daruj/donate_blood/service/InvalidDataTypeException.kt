package net.jsoft.daruj.donate_blood.service

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.DarujException
import net.jsoft.daruj.common.misc.asUiText

class InvalidDataTypeException(val type: String) : DarujException(
    message = "Invalid data type $type!",
    uiText = R.string.tx_unknown_error.asUiText()
)