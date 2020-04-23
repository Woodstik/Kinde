package com.wstik.kinde.presentation.about

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.wstik.kinde.BuildConfig
import com.wstik.kinde.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.row_profile_option.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

fun Context.startAbout() {
    startActivity(Intent(this, AboutActivity::class.java))
}

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(layoutToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle(R.string.about_title)

        textVersionInfo.text = getString(R.string.app_version_format, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
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
}
