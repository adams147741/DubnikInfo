package com.example.dubnikinfo.data.local.trash

import androidx.annotation.DrawableRes
import com.example.dubnikinfo.R

enum class TrashType(@DrawableRes val id: Int, val enumId: Int, val title: String) {
    NONE(0, 0, "Žiadny"),
    MUNICIPAL(R.drawable.zbertko, 1, "Komunálny odpad"),
    PLASTIC(0, 2, "Plasty"),
    GLASS(R.drawable.zbersklo, 3, "Sklo"),
    ELECTRIC(0, 4, "Elektronika"),
    CLOTHES(0, 5, "Oblečenie"),
    BIOLOGICAL(0, 6, "Biologický"),
    COOKING_OIL(0, 7, "Kuchyský olej"),
    PAPER(0, 8, "Papier");

    companion object {
        fun fromEnumId(enumId: Int): TrashType {
            return entries.find { it.enumId == enumId } ?: NONE
        }
    }
}