package com.wstik.kinde.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wstik.kinde.R
import com.wstik.kinde.presentation.home.HomeFragment
import com.wstik.kinde.presentation.inbox.InboxFragment
import com.wstik.kinde.presentation.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_rules.layoutToolbar
import kotlinx.android.synthetic.main.toolbar.view.*

fun Context.startMain() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(layoutToolbar.toolbar)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_home -> showFragment(HomeFragment.newInstance(), R.string.home_title)
                R.id.item_inbox -> showFragment(InboxFragment.newInstance(), R.string.inbox_title)
                R.id.item_profile -> showFragment(
                    ProfileFragment.newInstance(),
                    R.string.profile_title
                )
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomNavigation.selectedItemId = R.id.item_home
    }

    private fun showFragment(fragment: Fragment, @StringRes title: Int) {
        setTitle(title)
        supportFragmentManager.beginTransaction().replace(R.id.layoutFragment, fragment).commit()
    }
}
