package com.tearsdr0p.scanskin.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tearsdr0p.scanskin.data.local.model.User
import com.tearsdr0p.scanskin.data.pref.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(
    private val pref: UserPreferences
) : ViewModel() {

    fun getUser(): LiveData<User> = pref.getUser().asLiveData()

    fun logout(){
        viewModelScope.launch {
            pref.clearToken()
        }
    }
}