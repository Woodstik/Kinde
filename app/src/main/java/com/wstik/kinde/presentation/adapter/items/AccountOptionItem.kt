package com.wstik.kinde.presentation.adapter.items

import com.wstik.kinde.R
import com.wstik.kinde.data.models.AccountOption

class AccountOptionItem(val accountOption: AccountOption) : AdapterItem {
    override fun getViewType(): Int = R.layout.row_account_option

    override fun isSameItem(otherItem: AdapterItem): Boolean {
        return isSameType(otherItem) && accountOption.title == (otherItem as AccountOptionItem).accountOption.title
    }

    override fun isSameContent(otherItem: AdapterItem): Boolean {
        return accountOption == (otherItem as AccountOptionItem).accountOption
    }
}