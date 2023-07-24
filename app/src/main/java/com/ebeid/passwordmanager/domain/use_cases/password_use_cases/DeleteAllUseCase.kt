package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import javax.inject.Inject

class DeleteAllUseCase
@Inject
constructor(private val repository: PasswordRepository) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}