package com.tearsdr0p.scanskin.ui.result

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearsdr0p.scanskin.data.local.room.HistoryDao
import com.tearsdr0p.scanskin.data.local.room.HistoryEntity
import com.tearsdr0p.scanskin.repository.HistoryRepository
import kotlinx.coroutines.launch

class ResultViewModel(app: Application = Application()): ViewModel() {
    private val historyRepository = HistoryRepository(app)
    fun insertHistory(result: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.insertHistory(result)
        }
    }
}