package com.wstik.kinde.presentation.account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wstik.kinde.R
import com.wstik.kinde.data.enums.LoadState
import com.wstik.kinde.data.models.AccountOption
import com.wstik.kinde.presentation.auth.changepassword.startChangePassword
import com.wstik.kinde.presentation.auth.startAuthMain
import com.wstik.kinde.presentation.deleteaccount.startDeleteAccount
import kotlinx.android.synthetic.main.activity_about.layoutToolbar
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

fun Context.startAccount() {
    startActivity(Intent(this, AccountActivity::class.java))
}

class AccountActivity : AppCompatActivity() {

    private val viewModel: AccountViewModel by viewModel()
    private val adapter by lazy { AccountAdapter(accountListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        viewModel.logoutState.observe(this, Observer { handleLogoutState(it) })

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.account_title)

        listAccount.layoutManager = LinearLayoutManager(this)
        listAccount.adapter = adapter

        val accountOptions = listOf(
            AccountOption(R.drawable.ic_change_password, R.string.item_change_password),
            AccountOption(R.drawable.ic_logout, R.string.item_logout),
            AccountOption(R.drawable.ic_delete_account, R.string.item_delete_account)
        )
        adapter.showAccountOptions(accountOptions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleLogoutState(state: LoadState<Unit>){
        when(state){
            is LoadState.Data -> startAuthMain()
            is LoadState.Error -> Toast.makeText(this, R.string.error_logout, Toast.LENGTH_SHORT).show()
        }
    }

    private val accountListener = object : AccountListener {
        override fun onOptionSelected(option: AccountOption) {
            when (option.title) {
                R.string.item_change_password -> startChangePassword()
                R.string.item_logout -> {
                    AlertDialog.Builder(this@AccountActivity)
                        .setTitle(R.string.logout_title)
                        .setMessage(R.string.logout_message)
                        .setPositiveButton(R.string.btn_logout) { _, _ -> viewModel.logout() }
                        .setNegativeButton(R.string.btn_cancel, null)
                        .show()
                }
                R.string.item_delete_account -> startDeleteAccount()
            }
        }
    }
}
