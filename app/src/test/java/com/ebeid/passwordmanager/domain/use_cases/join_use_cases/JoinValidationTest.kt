package com.ebeid.passwordmanager.domain.use_cases.join_use_cases

import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class JoinValidationTest {
    @Mock
    lateinit var mockPasswordRepository: PasswordRepository

    private lateinit var joinUseCase: JoinUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        joinUseCase = JoinUseCase(mockPasswordRepository)
    }

    @Test
    fun `test password validation with matching passwords`() {
        val result = joinUseCase.validate("password123", "password123")
        assertThat(result).isEmpty()
    }

    @Test
    fun `test password validation with non-matching passwords`() {
        val result = joinUseCase.validate("password123", "pass123")
        assertThat(result).isEqualTo("Check Your Password")
    }

    @Test
    fun `test password validation with password length less than 6 characters`() {
        val result = joinUseCase.validate("pass", "pass")
        assertThat(result).isEqualTo("Password must be between 6 and 26 chars")
    }

    @Test
    fun `test password validation with password length greater than 25 characters`() {
        val result = joinUseCase.validate(
            "thisisaverylongpasswordthatisgreaterthan25chars",
            "thisisaverylongpasswordthatisgreaterthan25chars"
        )
        assertThat(result).isEqualTo("Password must be between 6 and 26 chars")
    }

}