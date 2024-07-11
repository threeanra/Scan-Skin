package com.tearsdr0p.scanskin.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.tearsdr0p.scanskin.data.local.room.HistoryEntity
import com.tearsdr0p.scanskin.data.local.room.ScanSkinDatabase

class HistoryRepository(application: Application) {
    private val historyDao = ScanSkinDatabase.getDatabase(application).historyDao()

    suspend fun insertHistory(historyEntity: HistoryEntity) {
        historyDao.insert(historyEntity)
    }

    suspend fun deleteHistory(historyEntity: HistoryEntity) {
        historyDao.delete(historyEntity)
    }

    fun getHistory(): LiveData<List<HistoryEntity>> = historyDao.getAllHistory()
}