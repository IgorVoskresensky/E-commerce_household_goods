package ru.ivos.e_commerce_household_goods.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ivos.e_commerce_household_goods.utils.INTRODUCTION_SP
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFireBaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore() = Firebase.firestore

    @Provides
    fun provideIntroductionSP(
        application: Application
    ) = application.getSharedPreferences(INTRODUCTION_SP, MODE_PRIVATE)
}