package com.tearsdr0p.scanskin.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tearsdr0p.scanskin.data.local.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveUser(user : User) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = user.id
            preferences[NAME] = user.name
            preferences[EMAIL] = user.email
            preferences[AUTH] = user.token
        }
    }

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[ID_KEY] ?: "",
                preferences[NAME] ?: "John Doe",
                preferences[EMAIL]  ?: "",
                preferences[AUTH] ?: ""
            )
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = ""
            preferences[NAME] = ""
            preferences[EMAIL] = ""
            preferences[AUTH] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        private val ID_KEY = stringPreferencesKey("id")
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val AUTH = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}