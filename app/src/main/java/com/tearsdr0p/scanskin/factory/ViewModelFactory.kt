package com.tearsdr0p.scanskin.factory

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tearsdr0p.scanskin.data.local.room.HistoryDao
import com.tearsdr0p.scanskin.data.pref.UserPreferences
import com.tearsdr0p.scanskin.data.pref.dataStore
import com.tearsdr0p.scanskin.ui.history.HistoryViewModel
import com.tearsdr0p.scanskin.ui.onboarding.AuthViewModel
import com.tearsdr0p.scanskin.ui.result.ResultViewModel

class ViewModelFactory private constructor(
    private val pref: UserPreferences,
    private val app: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(app) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(app) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    pref = UserPreferences.getInstance(context.dataStore),
                    app = context.applicationContext as Application
                )
            }.also { instance = it }
    }
}