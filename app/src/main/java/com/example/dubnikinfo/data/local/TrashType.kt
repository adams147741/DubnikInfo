package com.example.dubnikinfo.data.local

import androidx.annotation.DrawableRes
import com.example.dubnikinfo.R

enum class TrashType(@DrawableRes val id: Int) {
    NONE(0),
    MUNICIPAL(R.drawable.zbertko),
    PLASTIC(0),
    GLASS(0),
    ELECTRIC(0),
    CLOTHES(0),
    BIOLOGICAL(0),
    COOKING_OIL(0),
    PAPER(0)
}