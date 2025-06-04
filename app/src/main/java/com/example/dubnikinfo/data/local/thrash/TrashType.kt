package com.example.dubnikinfo.data.local.thrash

import androidx.annotation.DrawableRes
import com.example.dubnikinfo.R

enum class TrashType(@DrawableRes val id: Int, val enumId: Int) {
    NONE(0, 0),
    MUNICIPAL(R.drawable.zbertko, 1),
    PLASTIC(0, 2),
    GLASS(R.drawable.zbersklo, 3),
    ELECTRIC(0, 4),
    CLOTHES(0, 5),
    BIOLOGICAL(0, 6),
    COOKING_OIL(0, 7),
    PAPER(0, 8)
}