package com.wstik.kinde.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.wstik.kinde.BuildConfig
import com.wstik.kinde.R


fun Context.hideKeyboard(view: View){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showErrorDialog(message: String, title: String = getString(R.string.error_dialog_title)){
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.btn_ok, null)
        .show()
}

fun Context.shareApp(){
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app_title))
    sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message, BuildConfig.APPLICATION_ID))
    startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.share_with)))
}