package uk.co.lung9182uk.trackdemo.util

import android.content.Context
import uk.co.lung9182uk.trackdemo.util.extension.isNetworkConnected
import javax.inject.Inject


class NetworkUtils @Inject constructor(val context: Context) {

    fun isNetworkConnected(): Boolean {
        return context.isNetworkConnected()
    }
}