package com.wstik.kinde.data.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AccountOption (
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)