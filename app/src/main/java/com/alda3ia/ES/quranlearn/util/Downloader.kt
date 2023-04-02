package com.alda3ia.ES.quranlearn.util

import android.app.DownloadManager
import android.content.Context
import android.icu.text.CaseMap.Title
import android.os.Environment
import androidx.core.net.toUri

class Downloader(context: Context) {

    private val downloadManager=context.getSystemService(DownloadManager::class.java)

    fun dowload(url:String,title:String):Long{
        val request=DownloadManager.Request(url.toUri())
            .setMimeType("video/mp4")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("$title.mp4")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"$title.mp4")

        return downloadManager.enqueue(request)

    }
}