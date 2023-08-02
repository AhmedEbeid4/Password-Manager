package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.ebeid.passwordmanager.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePasswordUseCase
@Inject
constructor(
    private val passwordRepository: PasswordRepository
) {
    suspend operator fun invoke(
        title: String,
        account: String,
        username: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        val validationMessage = validate(title, account, username, password)
        if (validationMessage.isNotEmpty()) {
            onFail(validationMessage)
            return
        }
        val res = withContext(Dispatchers.Default) {
            passwordRepository.savePasswordEntity(
                PasswordModel(
                    title = title.trim(),
                    account = account.trim(),
                    username = username.trim(),
                    password = password.trim()
                )
            )
        }
        when (res) {
            is Result.Success -> {
                onSuccess()
            }

            is Result.Error -> {
                onFail(res.exception.message.toString())
            }
        }
    }

    fun validate(
        title: String,
        account: String,
        username: String,
        password: String
    ): String {
        if (title.trim().isEmpty()) {
            return "title is empty"
        }
        if (title.trim().length > 35) {
            return "title is invalid"
        }
        if (account.trim().isEmpty()) {
            return "account is empty"
        }
        if (account.trim().length > 35) {
            return "account is invalid"
        }
        if (username.trim().isEmpty()) {
            return "username is empty"
        }
        if (username.trim().length > 35) {
            return "username is invalid"
        }
        if (password.length < 6 || password.length > 20) {
            return "Invalid Password"
        }
        return ""
    }
}