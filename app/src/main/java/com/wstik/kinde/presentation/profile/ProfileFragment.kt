package com.wstik.kinde.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wstik.kinde.BuildConfig
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.ProfileOption
import com.wstik.kinde.data.models.UserProfile
import com.wstik.kinde.presentation.about.startAbout
import com.wstik.kinde.presentation.account.startAccount
import com.wstik.kinde.presentation.adapter.items.ProfileOptionItem
import com.wstik.kinde.presentation.notifications.startNotifications
import com.wstik.kinde.presentation.user.startUser
import com.wstik.kinde.utils.shareApp
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModel()
    private val adapter by lazy { ProfileAdapter(profileListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.profileState.observe(this, Observer { handleProfileState(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listProfile.layoutManager = LinearLayoutManager(context)
        listProfile.adapter = adapter
    }

    private fun handleProfileState(state: LoadState<UserProfile>) {
        when (state) {
            is LoadState.Data -> {
                val profileOptions = listOf(
                    ProfileOption(R.drawable.ic_account, R.string.item_account, getString(R.string.item_account_description)),
                    ProfileOption(R.drawable.ic_notifications, R.string.item_notifications, getString(R.string.item_notifications_description)),
                    ProfileOption(R.drawable.ic_about, R.string.item_about, getString(R.string.item_about_description, BuildConfig.VERSION_NAME)),
                    ProfileOption(R.drawable.ic_share_app, R.string.item_share_app))
                adapter.showProfile(state.data, profileOptions)
            }
            is LoadState.Error -> Toast.makeText(context, R.string.error_load_profile, Toast.LENGTH_SHORT).show()
        }
    }

    private val profileListener = object : ProfileListener {
        override fun onHeaderSelected() {
            context?.startUser()
        }

        override fun onOptionSelected(option: ProfileOption) {
            when(option.title){
                R.string.item_account -> context?.startAccount()
                R.string.item_notifications -> context?.startNotifications()
                R.string.item_about -> context?.startAbout()
                R.string.item_share_app -> context?.shareApp()
            }
        }
    }
}
