package com.ebeid.passwordmanager.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ebeid.passwordmanager.domain.model.PasswordModel

@Database(entities = [PasswordModel::class], version = 1, exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}