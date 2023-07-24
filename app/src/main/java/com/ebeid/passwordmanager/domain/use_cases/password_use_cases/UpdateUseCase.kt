package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class UpdateUseCase
@Inject
constructor(private val repository: PasswordRepository) {
    suspend operator fun invoke(
        passwordModel: PasswordModel,
        onSuccess: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        val validationMessage = validate(
            passwordModel.title,
            passwordModel.account,
            passwordModel.username,
            passwordModel.password
        )
        if (validationMessage.isNotEmpty()) {
            onFail(validationMessage)
            return
        }
        repository.update(passwordModel)
        onSuccess()
    }

    private fun validate(
        title: String,
        account: String,
        username: String,
        password: String
    ): String {
        if (title.isEmpty()) {
            return "title is empty"
        }
        if (title.length > 35) {
            return "title is invalid"
        }
        if (account.isEmpty()) {
            return "account is empty"
        }
        if (account.length > 35) {
            return "account is invalid"
        }
        if (username.isEmpty()) {
            return "username is empty"
        }
        if (username.length > 35) {
            return "username is invalid"
        }
        if (password.length < 6 || password.length > 20) {
            return "Invalid Password"
        }
        return ""
    }
}