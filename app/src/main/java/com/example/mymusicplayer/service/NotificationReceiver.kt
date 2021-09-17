package com.example.mymusicplayer.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.sendBroadcast(Intent("action_name")
            .putExtra("action_name", intent.action))
    }
}