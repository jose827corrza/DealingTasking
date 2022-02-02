package com.josedev.dealingtasking.firebasecrud.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.josedev.dealingtasking.FirebaseApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsDatastore
@Inject
constructor(
    private val context: FirebaseApplication
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    val settingsPrefFlow: Flow<UserSettings> = context.dataStore.data
        .catch {exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map {preference ->
            val userSaved = preference[USER_EMAIL_SAVED] ?: ""
            val userPsswdSaved = preference[USER_PSSWD_SAVED] ?: ""
            UserSettings(
                emailUser = userSaved,
                psswdUser = userPsswdSaved
            )
        }

    suspend fun toggleUserSettings(userToBeSaved: String, psswdToBeSaved: String){
        context.dataStore.edit { preference ->
            preference[USER_EMAIL_SAVED] = userToBeSaved
            preference[USER_PSSWD_SAVED] = psswdToBeSaved
        }
    }

    companion object{
        val USER_EMAIL_SAVED = stringPreferencesKey("userSaved")
        val USER_PSSWD_SAVED = stringPreferencesKey("psswdSaved")
    }
}

data class UserSettings(
    val emailUser: String,
    val psswdUser: String
)