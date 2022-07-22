package net.jsoft.daruj.common.util

import android.content.Context

fun Context.countriesSortedBySerbianAlphabet(): List<Country> {
    val alphabet = arrayOf(
        "A",
        "B",
        "C",
        "Č",
        "Ć",
        "D",
        "DŽ",
        "Đ",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "LJ",
        "M",
        "N",
        "NJ",
        "O",
        "P",
        "R",
        "S",
        "Š",
        "T",
        "U",
        "V",
        "Z",
        "Ž"
    )

    val countries = Country.values().sortedBy { country ->
        val countryName = getString(country.resId).uppercase()
        val countryModified = countryName
            .replace("DŽ", Char(6).toString())
            .replace("LJ", Char(16).toString())
            .replace("NJ", Char(19).toString())

        String(
            chars = countryModified.map { char ->
                if (char in arrayOf(Char(6), Char(16), Char(19))) {
                    char
                } else {
                    alphabet.indexOf(char.toString()).toChar()
                }
            }.toCharArray()
        )
    }

    return countries
}