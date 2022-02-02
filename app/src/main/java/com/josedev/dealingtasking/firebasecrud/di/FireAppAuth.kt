package com.josedev.dealingtasking.firebasecrud.di

import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.messaging.FirebaseMessaging
import com.josedev.dealingtasking.R

class FireAppAuth(val auth: FirebaseAuth) {

    fun createNewUser(newUserEmail: String, newUserPsswd: String){
        auth.createUserWithEmailAndPassword(newUserEmail, newUserPsswd).addOnCompleteListener { it ->
            if(it.isSuccessful){
                Log.d("AUTH", "usuario creado")
            }else{
                Log.d("AUTH", "problema al crear user")
            }
        }
    }

    fun signInUser(userEmail: String, userPsswd: String){
        auth.signInWithEmailAndPassword(userEmail, userPsswd).addOnCompleteListener { it ->
            if(it.isSuccessful){
                Log.d("AUTH", "usuario logeado")

            }else{
                Log.d("AUTH", "problema al logearse user")

            }
        }
    }

    fun authentication(): String {
        val currentUser = auth.currentUser
        var userActual = ""

        if(currentUser != null){
            userActual = currentUser.displayName!!
        }else{
            userActual = "noUser"
        }
        return userActual
    }

    fun signAccountOff(){
        auth.signOut()

    }

    private fun notification(){

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if(task.isSuccessful){
                return@OnCompleteListener
            }
            val token = task.result
            println(token)

            // Log and toast



        })
    }

}