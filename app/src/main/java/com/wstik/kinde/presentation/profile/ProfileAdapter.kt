package com.wstik.kinde.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wstik.kinde.R
import com.wstik.kinde.data.models.ProfileOption
import com.wstik.kinde.data.models.UserProfile
import com.wstik.kinde.presentation.adapter.AdapterItemDiffCallback
import com.wstik.kinde.presentation.adapter.items.AdapterItem
import com.wstik.kinde.presentation.adapter.items.ProfileHeaderItem
import com.wstik.kinde.presentation.adapter.items.ProfileOptionItem
import com.wstik.kinde.presentation.adapter.viewholder.AdapterItemViewHolder
import kotlinx.android.synthetic.main.row_profile_header.view.*
import kotlinx.android.synthetic.main.row_profile_option.view.*

class ProfileAdapter(private val profileListener: ProfileListener) : RecyclerView.Adapter<AdapterItemViewHolder>() {

    private val items = mutableListOf<AdapterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterItemViewHolder {
        val rowView = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.row_profile_header -> ProfileHeaderViewHolder(rowView, profileListener)
            R.layout.row_profile_option -> ProfileOptionViewHolder(rowView, profileListener)
            else -> throw IllegalArgumentException("ProfileAdapter: Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun onBindViewHolder(holder: AdapterItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    fun showProfile(profileHeader: UserProfile) {
        val newItems = mutableListOf<AdapterItem>()
        newItems.add(ProfileHeaderItem(profileHeader))

        newItems.add(ProfileOptionItem(ProfileOption(R.drawable.ic_account, R.string.item_account, R.string.item_account_description)))
        newItems.add(
            ProfileOptionItem(
                ProfileOption(
                    R.drawable.ic_notifications,
                    R.string.item_notifications,
                    R.string.item_notifications_description
                )
            )
        )
        newItems.add(ProfileOptionItem(ProfileOption(R.drawable.ic_about, R.string.item_about, R.string.item_about_description)))
        newItems.add(ProfileOptionItem(ProfileOption(R.drawable.ic_share_app, R.string.item_share_app)))

        val diffResult = DiffUtil.calculateDiff(AdapterItemDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ProfileHeaderViewHolder(itemView: View, private val profileListener: ProfileListener) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val profileHeader = (item as ProfileHeaderItem).userProfile
        itemView.setOnClickListener { profileListener.onHeaderSelected() }
        itemView.textInitial.text = profileHeader.name[0].toString()
        itemView.textName.text = profileHeader.name
        itemView.textEmail.text = profileHeader.email
    }
}

class ProfileOptionViewHolder(itemView: View, private val profileListener: ProfileListener) : AdapterItemViewHolder(itemView) {
    override fun bindItem(item: AdapterItem) {
        val profileOption = (item as ProfileOptionItem).profileOption
        itemView.setOnClickListener { profileListener.onOptionSelected(profileOption) }
        itemView.imageOption.setImageResource(profileOption.icon)
        itemView.textTitle.setText(profileOption.title)
        if (profileOption.description > 0) {
            itemView.textDescription.visibility = View.VISIBLE
            itemView.textDescription.setText(profileOption.description)
        } else {
            itemView.textDescription.visibility = View.GONE
        }
    }
}

interface ProfileListener {
    fun onHeaderSelected()
    fun onOptionSelected(option: ProfileOption)
}

