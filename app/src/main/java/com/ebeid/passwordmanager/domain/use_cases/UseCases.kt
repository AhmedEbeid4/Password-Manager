package com.ebeid.passwordmanager.domain.use_cases

import com.ebeid.passwordmanager.domain.use_cases.join_use_cases.GetPasswordUseCase
import com.ebeid.passwordmanager.domain.use_cases.join_use_cases.JoinUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.DeleteAllUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.DeletePasswordUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.GetPasswordsUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.SavePasswordUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.SearchUseCase
import com.ebeid.passwordmanager.domain.use_cases.password_use_cases.UpdateUseCase
import javax.inject.Inject

class UseCases
@Inject
constructor(
    val joinUseCase: JoinUseCase,
    val getPasswordUseCase: GetPasswordUseCase,
    val savePasswordUseCase: SavePasswordUseCase,
    val getAllPasswordUseCase: GetPasswordsUseCase,
    val deleteAllUseCase: DeleteAllUseCase,
    val searchUseCase: SearchUseCase,
    val deletePasswordUseCase: DeletePasswordUseCase,
    val updateUseCase: UpdateUseCase
)