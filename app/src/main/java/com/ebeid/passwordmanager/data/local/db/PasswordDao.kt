package com.ebeid.passwordmanager.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.utils.Constants.PASSWORDS_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM $PASSWORDS_TABLE")
    fun getAllPassword(): Flow<MutableList<PasswordModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePassword(passwordModel: PasswordModel)

    @Query("DELETE FROM $PASSWORDS_TABLE")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(passwordModel: PasswordModel)

    @Update
    suspend fun update(passwordModel: PasswordModel)

    @Query("SELECT * FROM $PASSWORDS_TABLE WHERE title LIKE '%' || :text || '%' OR account LIKE '%' || :text || '%' OR username LIKE '%' || :text || '%'")
    suspend fun searchPassword(text: String): MutableList<PasswordModel>
}