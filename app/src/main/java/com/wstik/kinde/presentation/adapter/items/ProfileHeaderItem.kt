package com.wstik.kinde.presentation.adapter.items

import com.wstik.kinde.R
import com.wstik.kinde.data.models.UserProfile

class ProfileHeaderItem(val userProfile: UserProfile) : AdapterItem {

    override fun getViewType(): Int = R.layout.row_profile_header

    override fun isSameItem(otherItem: AdapterItem): Boolean {
       return isSameType(otherItem)
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return userProfile == (otherItem as ProfileHeaderItem).userProfile
    }
}