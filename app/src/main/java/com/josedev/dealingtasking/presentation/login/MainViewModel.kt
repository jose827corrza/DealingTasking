package com.josedev.dealingtasking.presentation.login

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josedev.dealingtasking.firebasecrud.datastore.SettingsDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val settingsUser: SettingsDatastore
): ViewModel(){

    val settingPref =settingsUser.settingsPrefFlow

    fun toggleSettings(email: String, psswd: String){
        viewModelScope.launch {
            settingsUser.toggleUserSettings(email, psswd)
        }
    }
}