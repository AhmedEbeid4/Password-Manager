package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPasswordsUseCase @Inject
constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke() : Flow<MutableList<PasswordModel>> {
        return passwordRepository.getAllPasswords()
    }
}