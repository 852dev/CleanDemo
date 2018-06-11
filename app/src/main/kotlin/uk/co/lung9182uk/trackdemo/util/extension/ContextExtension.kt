package uk.co.lung9182uk.trackdemo.util.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.inputmethod.InputMethodManager
import timber.log.Timber

fun Context.isNetworkConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo?.isConnectedOrConnecting ?: false
}


fun Context.hideKeyboard() {
    try {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if ((this as Activity).currentFocus == null || this.currentFocus.windowToken == null) {
            return
        }
        inputManager.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    } catch (exception: Exception) {
        Timber.d(exception.localizedMessage)
    }
}