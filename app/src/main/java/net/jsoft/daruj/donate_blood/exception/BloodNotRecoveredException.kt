package net.jsoft.daruj.donate_blood.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.exception.DarujException
import net.jsoft.daruj.common.misc.asUiText

class BloodNotRecoveredException(days: Int) : DarujException(
    message = "Cannot donate blood for $days days.",
    uiText = R.string.tx_you_cannot_donate_blood.asUiText(days)
)