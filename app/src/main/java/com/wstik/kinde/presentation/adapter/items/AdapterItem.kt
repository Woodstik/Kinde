package com.wstik.kinde.presentation.adapter.items

import androidx.annotation.LayoutRes

interface AdapterItem {

    @LayoutRes
    fun getViewType() : Int

    fun isSameItem(otherItem: AdapterItem) : Boolean = false

    fun isSameContent(otherItem: AdapterItem) : Boolean = false

    fun isSameType(otherItem: AdapterItem) : Boolean{
        return getViewType() == otherItem.getViewType()
    }
}