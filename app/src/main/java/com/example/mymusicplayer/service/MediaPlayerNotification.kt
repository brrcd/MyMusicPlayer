package com.example.mymusicplayer.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.mymusicplayer.R
import com.example.mymusicplayer.model.entity.Song

class MediaPlayerNotification : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        startForegroundService(Intent(this, MediaPlayerNotification::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context: Context, song: Song) {

        val name = context.getString(R.string.notification_channel_name)
        val descriptionText = context.getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
        notificationChannel.description = descriptionText
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = Notification.Builder(context, "15")
            .setContentTitle(song.title)
            .setStyle(Notification.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(context.resources, song.coverRes))
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(action(pendingIntent(context, ACTION_LOOP), "Loop"))
            .addAction(action(pendingIntent(context, ACTION_PLAY), "Play"))
            .addAction(action(pendingIntent(context, ACTION_SHUFFLE), "Random"))
            .build()

        notificationManager.notify(15, notification)
    }

    private fun action(pendingIntent: PendingIntent?, title: String): Notification.Action {
        return Notification.Action.Builder(
            R.drawable.ic_baseline_play_arrow_24,
            title,
            pendingIntent
        )
            .build()
    }

    private fun pendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java)
            .setAction(action)
        return PendingIntent.getBroadcast(
            context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        const val ACTION_PLAY = "play"
        const val ACTION_LOOP = "loop"
        const val ACTION_SHUFFLE = "shuffle"
        private const val CHANNEL_ID = "channel id"
    }
}