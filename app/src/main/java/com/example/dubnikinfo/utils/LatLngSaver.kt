package com.example.dubnikinfo.utils

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.google.android.gms.maps.model.LatLng

/**
 * Saver for LatLng objects
 * @param LatLng? - the LatLng object to be saved
 */
val LatLngSaver: Saver<LatLng?, Any> = listSaver(
    save = { listOf(it?.latitude, it?.longitude)},
    restore =  { (it as? List<*>)?.let { list -> LatLng(list[0] as Double, list[1] as Double) } }
)