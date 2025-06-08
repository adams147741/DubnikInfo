package com.example.dubnikinfo.domain.repository

import androidx.room.Entity
import com.example.dubnikinfo.data.local.trash.TrashDao
import com.example.dubnikinfo.data.local.trash.TrashEntity
import com.example.dubnikinfo.data.local.trash.TrashPickup
import com.example.dubnikinfo.data.local.trash.TrashType
import com.example.dubnikinfo.data.local.trash.entityToPickup
import com.example.dubnikinfo.data.local.trash.pickupToEntity
import com.example.dubnikinfo.data.remote.FirebaseTrash
import com.example.dubnikinfo.utils.formatTimestampToLocalDate
import com.example.dubnikinfo.utils.parseLongToTimeStamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

interface TrashRepository {
    suspend fun insertTrash(trash: TrashEntity)
    suspend fun getTrashOnline()
    suspend fun getTrashMap(): Map<LocalDate, List<TrashType>>
    suspend fun getFirstPickups(): Map<LocalDate, List<TrashType>>
}

class TrashRepositoryImpl(
    private val trashDao: TrashDao,
    private val remote: FirebaseTrash
) : TrashRepository {

    /**
     * Inserts a trash pickup into the local database
     * @param trash TrashEntity - the trash pickup to be inserted
     */
    override suspend fun insertTrash(trash: TrashEntity) {
        trashDao.insertTrash(trash)
    }

    /**
     * Gets trash pickups from the remote database and inserts them into the local database
     */
    override suspend fun getTrashOnline() {
        val trashList = remote.getTrashPickups()
        if (trashList.isNotEmpty()) {
            trashDao.deleteAllTrash()
            trashList.forEach { trash ->
                trashDao.insertTrash(pickupToEntity(trash))
            }
        }
    }

    /**
     * Returns a map of trash pickups grouped by date
     * Uses local database first if available
     * @return Map<LocalDate, List<TrashType>> - a map of trash pickups grouped by date
     */
    override suspend fun getTrashMap(): Map<LocalDate, List<TrashType>> {
        if (trashDao.getTrash().isEmpty()) {
            getTrashOnline()
        }
        val trashList = trashDao.getTrash()
        val trashMap: Map<LocalDate, List<TrashType>> = trashList
            .groupBy { entity ->
                formatTimestampToLocalDate(entityToPickup(entity).date)
            }
            .mapValues { entry ->
                entry.value.map { TrashType.fromEnumId(it.type) }
            }
        return trashMap
    }

    /**
     * Returns a map of trash pickups on the closest day from today
     * @return Map<LocalDate, List<TrashType>> - a map of trash pickups grouped by date
     */
    override suspend fun getFirstPickups(): Map<LocalDate, List<TrashType>> {
        val closestDay = trashDao.getClosestTrashDate(System.currentTimeMillis() / 1000)
        if (closestDay != null) {
            val entityList = trashDao.getTrashByDate(closestDay.date)
            val pickupList = entityList.map { entityToPickup(it) }
            val trashMap: Map<LocalDate, List<TrashType>> = pickupList
                .groupBy { pickup ->
                    formatTimestampToLocalDate(pickup.date)
                }
                .mapValues { entry ->
                    entry.value.map { TrashType.fromEnumId(it.type) }
                }
            return trashMap
        }
        return emptyMap()
    }
}