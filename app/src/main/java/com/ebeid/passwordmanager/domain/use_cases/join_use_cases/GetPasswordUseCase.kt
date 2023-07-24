package com.ebeid.passwordmanager.domain.use_cases.join_use_cases

import com.ebeid.passwordmanager.utils.EmptyPasswordException
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.ebeid.passwordmanager.utils.Result
import javax.inject.Inject

class GetPasswordUseCase
@Inject
constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(
        onSuccess: (pass: String) -> Unit,
        onFail: (message: String) -> Unit,
        onPasswordNotFound: () -> Unit
    ) {
        when (val res = passwordRepository.getPassword()) {
            is Result.Success -> {
                onSuccess((res).data)
            }

            is Result.Error -> {
                if (res.exception !is EmptyPasswordException) {
                    onFail(
                        res
                            .exception.message.toString()
                    )
                } else {
                    onPasswordNotFound()
                }
            }
        }
    }
}