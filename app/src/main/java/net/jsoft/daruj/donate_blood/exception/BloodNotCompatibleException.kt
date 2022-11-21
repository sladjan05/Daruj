package net.jsoft.daruj.donate_blood.exception

import net.jsoft.daruj.R
import net.jsoft.daruj.common.domain.model.Blood
import net.jsoft.daruj.common.exception.DarujException
import net.jsoft.daruj.common.misc.asUiText

class BloodNotCompatibleException(donor: Blood, recipient: Blood) : DarujException(
    message = "$donor cannot donate to $recipient.",
    uiText = R.string.tx_blood_types_not_compatible.asUiText()
)