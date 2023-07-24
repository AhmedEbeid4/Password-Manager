package com.ebeid.passwordmanager.domain.repo

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.utils.DataStatus
import com.ebeid.passwordmanager.utils.Result
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun join(password: String) : Result<Unit>
    fun getPassword() : Result<String>

    suspend fun savePasswordEntity(password: PasswordModel) : Result<Unit>

    fun getAllPasswords() : Flow<MutableList<PasswordModel>>

    suspend fun deleteAll()

    suspend fun filter(text: String): Flow<DataStatus<MutableList<PasswordModel>>>

    suspend fun delete(password: PasswordModel)

    suspend fun update(password: PasswordModel)
}