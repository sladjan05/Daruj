package net.jsoft.daruj.common.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import net.jsoft.daruj.R

inline fun <reified T> Activity.startActivity(
    vararg extras: Pair<String, Parcelable>
) {
    val bundle = Bundle()
    for (extra in extras) {
        bundle.putParcelable(extra.first, extra.second)
    }

    val intent = Intent(
        this,
        T::class.java
    )

    intent.putExtras(bundle)

    startActivity(intent)
    overridePendingTransition(
        R.anim.an_slide_in_from_right,
        R.anim.an_slide_out_to_left
    )
}

inline fun <reified T> Activity.switchActivity(
    vararg extras: Pair<String, Parcelable>
) {
    startActivity<T>(*extras)
    finish()
}

fun Activity.goBack() {
    finish()
    overridePendingTransition(
        R.anim.an_slide_in_from_left,
        R.anim.an_slide_out_to_right
    )
}