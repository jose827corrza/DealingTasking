package com.josedev.dealingtasking.firebasecrud.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.josedev.dealingtasking.FirebaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireInstance() = FirebaseFirestore.getInstance()



    @Provides
    @Singleton
    fun provideTaskList(
        firestore: FirebaseFirestore
    ) = firestore.collection("todosV2")

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context): FirebaseApplication{
        return app as FirebaseApplication
    }

//    @Singleton
//    @Provides
//    fun user() = FirebaseAuth.getInstance()


}