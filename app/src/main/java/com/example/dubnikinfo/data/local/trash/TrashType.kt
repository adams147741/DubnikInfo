package com.example.dubnikinfo.data.local.trash

import androidx.annotation.DrawableRes
import com.example.dubnikinfo.R

/**
 * Enum class representing a trash type
 * @param id Int - the drawable resource id
 * @param enumId Int - the enum id
 * @param title Int - the string resource id
 * @return TrashType - the trash type
 */
enum class TrashType(@DrawableRes val id: Int, val enumId: Int, val title: Int) {
    NONE(0, 0, R.string.None),
    MUNICIPAL(R.drawable.zbertko, 1, R.string.municipal_waste),
    PLASTIC(R.drawable.zberplast, 2, R.string.plastic_waste),
    GLASS(R.drawable.zbersklo, 3, R.string.glass_waste),
    ELECTRIC(R.drawable.zberelektro, 4, R.string.electronic_waste),
    CLOTHES(R.drawable.zbersaty, 5, R.string.clothing_waste),
    BIOLOGICAL(R.drawable.zberbio, 6, R.string.biological_waste),
    COOKING_OIL(R.drawable.zberolej, 7, R.string.cooking_oil_waste),
    PAPER(R.drawable.zberpapier, 8,R.string.paper_waste);

    companion object {
        fun fromEnumId(enumId: Int): TrashType {
            return entries.find { it.enumId == enumId } ?: NONE
        }
    }
}