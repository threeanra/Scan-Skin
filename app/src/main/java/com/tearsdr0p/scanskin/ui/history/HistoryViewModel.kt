package com.tearsdr0p.scanskin.ui.history

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tearsdr0p.scanskin.data.local.room.HistoryEntity
import com.tearsdr0p.scanskin.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(app: Application = Application()) : ViewModel() {
    private val historyRepository = HistoryRepository(app)

    val historyList: LiveData<List<HistoryEntity>> = historyRepository.getHistory()

    fun deleteAnalysisResult(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            historyRepository.deleteHistory(historyEntity)
        }
    }

}