package com.ebeid.passwordmanager.presentation.viewmodel

import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.repo.PasswordRepository
import com.ebeid.passwordmanager.domain.use_cases.UseCases
import com.ebeid.passwordmanager.utils.DataStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class StorePasswordViewModelTest{
    /*
    @Mock
    lateinit var mockUseCases: UseCases

    private lateinit var viewModel: StorePasswordViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = StorePasswordViewModel(mockUseCases)
    }


    @Test
    fun `test search with empty text`() = runBlockingTest {
        viewModel.search(null)
        val passwordsList = viewModel.passwordsList.value
        assertThat(DataStatus.success(listOf<PasswordModel>(), true)).isEqualTo(null)
    }
     */
}