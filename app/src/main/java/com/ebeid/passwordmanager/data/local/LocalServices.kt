package com.ebeid.passwordmanager.data.local

import com.ebeid.passwordmanager.data.local.db.PasswordDatabase
import javax.inject.Inject

class LocalServices
@Inject
constructor(
    val passwordDatabase: PasswordDatabase,
    val localPasswordService: LocalPasswordService
)