package com.wstik.kinde.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileOption (
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val description: String = ""
)