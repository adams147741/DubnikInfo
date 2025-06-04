package com.example.dubnikinfo.domain.repository

import com.example.dubnikinfo.data.local.thrash.ThrashDao
import com.example.dubnikinfo.data.local.thrash.ThrashEntity

interface ThrashRepository {
    suspend fun insertTrash(trash: ThrashEntity)
    suspend fun getTrashByDate(date: Long): List<ThrashEntity>
    suspend fun getTrash(): List<ThrashEntity>
}

class ThrashRepositoryImpl(private val thrashDao: ThrashDao) : ThrashRepository {
    override suspend fun insertTrash(trash: ThrashEntity) {
        thrashDao.insertThrash(trash)

    }

    override suspend fun getTrashByDate(date: Long): List<ThrashEntity> {
        return thrashDao.getThrashByDate(date)
    }

    override suspend fun getTrash(): List<ThrashEntity> {
        return thrashDao.getThrash()
    }
}