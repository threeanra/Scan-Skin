package com.tearsdr0p.scanskin.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tearsdr0p.scanskin.data.pref.UserPreferences
import com.tearsdr0p.scanskin.data.pref.dataStore
import com.tearsdr0p.scanskin.ui.onboarding.AuthViewModel

class ViewModelFactory private constructor(
    private val pref: UserPreferences,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(pref) as T
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
                )
            }.also { instance = it }
    }
}