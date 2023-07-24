package com.ebeid.passwordmanager.data.repo

import android.util.Log
import com.ebeid.passwordmanager.data.local.LocalServices
import com.ebeid.passwordmanager.utils.EmptyPasswordException
import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.ebeid.passwordmanager.utils.DataStatus
import com.ebeid.passwordmanager.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImp
@Inject
constructor(
    private val localServices: LocalServices
) : PasswordRepository {
    override suspend fun savePasswordEntity(password: PasswordModel): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                localServices.passwordDatabase.passwordDao().savePassword(password)
            }
            Log.i("CASE", password.password)
            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun filter(text: String): Flow<DataStatus<MutableList<PasswordModel>>> = flow {
        emit(DataStatus.loading())
        try {
            val result = localServices.passwordDatabase.passwordDao().searchPassword(text)
            emit(DataStatus.success(result, result.isEmpty()))
        } catch (e: Exception) {
            emit(DataStatus.error(e.message.toString()))
        }
    }

    override suspend fun delete(password: PasswordModel) = localServices.passwordDatabase.passwordDao().delete(password)
    override suspend fun update(password: PasswordModel) {
        Log.i("laksdmads",password.username)
        localServices.passwordDatabase.passwordDao().update(password)
    }

    override fun getAllPasswords() :Flow<MutableList<PasswordModel>>{
        val items = localServices.passwordDatabase.passwordDao().getAllPassword()
        Log.i("ITEM_PASSWORD",items.toString())
        return items
    }
    override suspend fun deleteAll() = localServices.passwordDatabase.passwordDao().deleteAll()


    override fun join(password: String): Result<Unit> {
        return try {
            localServices.localPasswordService.setPassword(password)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getPassword(): Result<String> {
        return try {
            val pass = localServices.localPasswordService.getPassword()
            if (pass.isNullOrEmpty()) {
                Result.Error(EmptyPasswordException())
            } else {
                Result.Success(pass)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


}