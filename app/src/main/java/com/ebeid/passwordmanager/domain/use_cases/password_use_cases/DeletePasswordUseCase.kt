package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class DeletePasswordUseCase
@Inject
constructor(private val repository: PasswordRepository){
    suspend operator fun invoke(passwordModel: PasswordModel) = repository.delete(passwordModel)
}