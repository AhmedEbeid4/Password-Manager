package com.ebeid.passwordmanager.domain.use_cases.password_use_cases

import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SaveValidationTest{
    @Mock
    lateinit var mockPasswordRepository: PasswordRepository

    private lateinit var savePasswordUseCase: SavePasswordUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        savePasswordUseCase = SavePasswordUseCase(mockPasswordRepository)
    }

    @Test
    fun `test validate function with valid inputs`() {
        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "Valid Username",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("")
    }

    @Test
    fun `test validate function with empty title`() {
        val validationMessage = savePasswordUseCase.validate(
            "",
            "Valid Account",
            "Valid Username",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("title is empty")
    }

    @Test
    fun `test validate function with invalid title`() {
        val validationMessage = savePasswordUseCase.validate(
            "This is a very long title that exceeds the maximum allowed length",
            "Valid Account",
            "Valid Username",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("title is invalid")
    }

    @Test
    fun `test validate function with empty account`() {
        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "",
            "Valid Username",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("account is empty")
    }

    @Test
    fun `test validate function with invalid account`() {
        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "This is a very long account that exceeds the maximum allowed length",
            "Valid Username",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("account is invalid")
    }

    @Test
    fun `test validate function with empty username`() {
        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("username is empty")
    }

    @Test
    fun `test validate function with invalid username`() {
        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "This is a very long username that exceeds the maximum allowed length",
            "ValidPassword123"
        )
        assertThat(validationMessage).isEqualTo("username is invalid")
    }

    @Test
    fun `test validate function with invalid password`() {

        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "Valid Username",
            "Short"
        )
        assertThat(validationMessage).isEqualTo("Invalid Password")
    }

    @Test
    fun `test validate function with valid minimum length password`() {

        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "Valid Username",
            "123456"
        )
        assertThat(validationMessage).isEqualTo("")
    }

    @Test
    fun `test validate function with valid maximum length password`() {

        val validationMessage = savePasswordUseCase.validate(
            "Valid Title",
            "Valid Account",
            "Valid Username",
            "12345678901234567890"
        )
        assertThat(validationMessage).isEqualTo("")
    }

}