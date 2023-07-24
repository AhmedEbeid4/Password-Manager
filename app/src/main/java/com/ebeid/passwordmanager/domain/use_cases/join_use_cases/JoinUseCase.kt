package com.ebeid.passwordmanager.domain.use_cases.join_use_cases

import com.ebeid.passwordmanager.utils.Result
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class JoinUseCase
@Inject
constructor(private val passwordRepository: PasswordRepository) {
    operator fun invoke(
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        val validationMessage = validate(password,confirmPassword)
        if(validationMessage.isNotEmpty()){
            onFail(validationMessage)
            return
        }
        when (passwordRepository.join(password)) {
            is Result.Success -> {
                onSuccess()
            }

            is Result.Error -> {
                onFail((passwordRepository.getPassword() as Result.Error).exception.message.toString())
            }
        }
    }
    private fun validate(password: String,confirmPassword: String):String{
        if(password != confirmPassword){
            return "Check Your Password"
        }
        if(password.length < 6 || password.length > 25){
            return "Password must be between 6 and 26 chars"
        }
        return ""
    }
}