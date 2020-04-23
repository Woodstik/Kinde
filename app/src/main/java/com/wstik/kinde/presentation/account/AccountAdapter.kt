package com.wstik.kinde.presentation.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wstik.kinde.R
import com.wstik.kinde.data.models.AccountOption
import com.wstik.kinde.presentation.adapter.AdapterItemDiffCallback
import com.wstik.kinde.presentation.adapter.items.AccountOptionItem
import com.wstik.kinde.presentation.adapter.items.AdapterItem
import com.wstik.kinde.presentation.adapter.viewholder.AdapterItemViewHolder
import kotlinx.android.synthetic.main.row_account_option.view.*

class AccountAdapter(val accountListener: AccountListener) : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_account_option -> AccountItemViewHolder(rowView, accountListener)
            else -> throw IllegalArgumentException("AccountAdapter: Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    fun showAccountOptions(options: List<AccountOption>) {
        val newItems = mutableListOf<AdapterItem>()

        options.forEach { newItems.add(AccountOptionItem(it)) }

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class AccountItemViewHolder(itemView: View, private val accountListener: AccountListener) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val accountOption = (item as AccountOptionItem).accountOption
        itemView.setOnClickListener { accountListener.onOptionSelected(accountOption) }
        itemView.imageOption.setImageResource(accountOption.icon)
        itemView.textTitle.setText(accountOption.title)
    }
}

interface AccountListener {
    fun onOptionSelected(option: AccountOption)
}