package com.example.dubnikinfo

import android.app.Application
import com.example.dubnikinfo.data.AppContainer
import com.example.dubnikinfo.data.DefaultAppContainer
import com.example.dubnikinfo.utils.LocalCoordinates
import com.example.dubnikinfo.work.NotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DubnikInfoApp : Application() {
    lateinit var appContainer: AppContainer
    private var applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer(this)
        applicationScope.launch {
            appContainer.trashRepository.getTrashOnline()
            appContainer.weatherRepository.updateTemperature(LocalCoordinates.latitude, LocalCoordinates.longitude)
        }
        NotificationWorker.schedule(this)
    }
}