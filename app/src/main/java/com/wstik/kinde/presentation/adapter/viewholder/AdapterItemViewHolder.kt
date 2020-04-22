package com.wstik.kinde.presentation.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wstik.kinde.presentation.adapter.items.AdapterItem

abstract class AdapterItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    abstract fun bindItem(item: AdapterItem)
}