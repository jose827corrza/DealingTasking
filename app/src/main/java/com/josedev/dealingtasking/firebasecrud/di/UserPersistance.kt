package com.josedev.dealingtasking.firebasecrud.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

data class UserPersistance(val correoSaved: String, val psswdSaved: String)