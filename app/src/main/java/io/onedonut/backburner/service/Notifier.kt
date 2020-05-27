package io.onedonut.backburner.service

import android.content.Context
import androidx.core.app.NotificationCompat
import io.onedonut.backburner.R
import javax.inject.Inject

class Notifier @Inject constructor(val context: Context) {

    fun notify(text: String) {
        val builder = NotificationCompat.Builder(context,
            CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_message_black_24dp)
            .setContentTitle("backburner item")
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    companion object {
        val CHANNEL_ID: String = TODO("Not yet defined")
    }

}