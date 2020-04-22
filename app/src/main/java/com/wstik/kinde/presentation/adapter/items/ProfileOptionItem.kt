package com.wstik.kinde.presentation.adapter.items

import com.wstik.kinde.R
import com.wstik.kinde.data.models.ProfileOption

class ProfileOptionItem(val profileOption: ProfileOption) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_profile_option

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameItem(otherItem) && profileOption.title == (otherItem as ProfileOptionItem).profileOption.title
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return profileOption == (otherItem as ProfileOptionItem).profileOption
    }
}