package com.example.dubnikinfo.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.dubnikinfo.DubnikInfoApp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.data.local.trash.TrashType
import java.util.concurrent.TimeUnit

class NotificationWorker (
    context: Context,
    params: WorkerParameters
    ) : CoroutineWorker(context, params) {
        private val appContainer = (applicationContext as DubnikInfoApp).appContainer
    private val newsRepository = appContainer.newsRepository
    private val trashRepository = appContainer.trashRepository

    override suspend fun doWork(): Result {
        createNotificationChannel()

        val localNews = newsRepository.getLocalActualities()
        val remoteNews = newsRepository.getOnlineActualities()
        if (localNews.size < remoteNews.size) {
            var message = "Na stránke pribudli nové aktuality!"
            if (remoteNews.size - localNews.size == 1) {
                message = remoteNews.first().title
            }
            sendNotification("Nové aktuality", message, R.drawable.mun_logo)
            newsRepository.update()
        }

        val closestTrash: List<TrashType> = trashRepository.getFirstPickups().values.flatten()
        var message = "Typ odpadu: "
        if (closestTrash.size > 0) {
            closestTrash.forEach { trashType ->
                message += trashType.title + ", "
            }
            if (closestTrash.size > 1) {
                sendNotification("Nezabudni na smeti!", message, R.drawable.mun_logo)
            } else {
                sendNotification("Nezabudni na smeti!", message, closestTrash[0].id)
            }
        }
        return Result.success()
    }

    private fun createNotificationChannel() {
        val name = "App Notificatons"
        val descriptionText = "Notification desc"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("default", name, importance)
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification(title: String, message: String, @androidx.annotation.DrawableRes icon: Int) {
        val notification = NotificationCompat.Builder(applicationContext, "default").
        setContentTitle(title).
        setContentText(message).
        setSmallIcon(icon).
        build()
        val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
        notificationManager.notify(title.hashCode(), notification)
    }

    companion object {
        fun schedule(context: Context) {
            val request =
                PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "minutely_notifications",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}