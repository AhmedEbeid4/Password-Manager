package com.ebeid.passwordmanager.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ebeid.passwordmanager.data.local.LocalPasswordService
import com.ebeid.passwordmanager.data.local.LocalServices
import com.ebeid.passwordmanager.data.local.db.PasswordDao
import com.ebeid.passwordmanager.data.local.db.PasswordDatabase
import com.ebeid.passwordmanager.data.repo.PasswordRepositoryImp
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.ebeid.passwordmanager.utils.Constants.PASSWORDS_DATABASE
import com.ebeid.passwordmanager.utils.Constants.SHARED_PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesPasswordsDB(@ApplicationContext context: Context): PasswordDatabase {
        return Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            PASSWORDS_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePasswordsDAO(passwordDB: PasswordDatabase): PasswordDao {
        return passwordDB.passwordDao()
    }

    @Provides
    @Singleton
    @Named("sharedPreferencesName")
    fun provideSharedPreferencesName(): String = SHARED_PREF_NAME

    @Provides
    @Singleton
    fun providePreferences(
        @ApplicationContext context: Context,
        @Named("sharedPreferencesName") sharedPreferencesName: String
    ): SharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePasswordService(
        preferences: SharedPreferences
    ): LocalPasswordService = LocalPasswordService(preferences,preferences.edit())

    @Provides
    @Singleton
    fun providePasswordRepo(
        localServices: LocalServices
    ): PasswordRepository = PasswordRepositoryImp(localServices)

}