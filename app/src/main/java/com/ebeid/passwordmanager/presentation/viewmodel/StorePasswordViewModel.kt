package com.ebeid.passwordmanager.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebeid.passwordmanager.domain.model.PasswordModel
import com.ebeid.passwordmanager.domain.use_cases.UseCases
import com.ebeid.passwordmanager.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StorePasswordViewModel
@Inject
constructor(
    private val useCases: UseCases
) : ViewModel() {
    private val _password = MutableStateFlow<String?>(null)
    val password: StateFlow<String?>
        get() = _password.asStateFlow()

    private val _passwordsList = MutableLiveData<DataStatus<List<PasswordModel>>>()
    val passwordsList: LiveData<DataStatus<List<PasswordModel>>>
        get() = _passwordsList

    fun initPasswords(){
        _passwordsList.value = DataStatus.success(mutableListOf(),true)
    }

    fun search(text: String?) {
        if (text == null) return
        val searchedText = text.trim()
        if (searchedText.isEmpty()) getAllPasswords()
        viewModelScope.launch {
            useCases.searchUseCase(searchedText).collect {
                _passwordsList.postValue(it)
            }
        }
    }

    fun getAllPasswords() = viewModelScope.launch {
        useCases.getAllPasswordUseCase()
            .catch {
                _passwordsList.postValue(DataStatus.error(it.message.toString()))
            }
            .collect {
                _passwordsList.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }

    fun deletePassword(index: Int, onComplete: () -> Unit, onFail: (message: String) -> Unit) =
        viewModelScope.launch {
            val password = getItemByIndex(index)
            if (password != null) {
                useCases.deletePasswordUseCase(password)
                viewModelScope.launch(Dispatchers.Main) { onComplete() }
            } else {
                viewModelScope.launch(Dispatchers.Main) { onFail("Something went wrong") }
            }
        }

    fun getPassword(
        onPasswordFound: () -> Unit,
        onFail: (message: String) -> Unit,
        onPasswordNotFound: () -> Unit
    ) {
        useCases.getPasswordUseCase({
            _password.value = it
            viewModelScope.launch(Dispatchers.Main) {
                onPasswordFound()
            }
        }, { mess ->
            viewModelScope.launch(Dispatchers.Main) {
                onFail(mess)
            }
        }, {
            viewModelScope.launch(Dispatchers.Main) {
                onPasswordNotFound()
            }
        })
    }

    fun generateRandomPassword(): String {
        val charset =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+-!@#\$%^&*()/\\><,"
        val password = StringBuilder()
        val random = java.util.Random()

        for (i in 0 until 10) {
            val randomIndex = random.nextInt(charset.length)
            val randomChar = charset[randomIndex]
            password.append(randomChar)
        }

        return password.toString()
    }


    fun getItemByIndex(index: Int): PasswordModel? {
        if (_passwordsList.value == null || index > _passwordsList.value!!.data!!.size) return null
        return _passwordsList.value!!.data!![index]
    }

    fun deleteAll(onFail: (message: String) -> Unit) {
        if (_passwordsList.value == null) {
            return
        }
        if (_passwordsList.value!!.isEmpty!!) {
            onFail("No Saved Passwords to delete")
            return
        }
        viewModelScope.launch {
            useCases.deleteAllUseCase()
            _passwordsList.postValue(
                DataStatus.success(mutableListOf(), true)
            )
        }
    }

    fun savePassword(
        title: String,
        account: String,
        username: String,
        password: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            useCases.savePasswordUseCase(
                title,
                account,
                username,
                password,
                {
                    viewModelScope.launch(Dispatchers.Main) {
                        onComplete()
                    }
                },
                {
                    viewModelScope.launch(Dispatchers.Main) {
                        onFail(it)
                    }
                }
            )
        }
    }


    fun updatePassword(
        passwordModel: PasswordModel,
        onFinish: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        viewModelScope.launch {
            useCases.updateUseCase(passwordModel, {
                viewModelScope.launch(Dispatchers.Main) { onFinish() }

            }, {
                viewModelScope.launch(Dispatchers.Main) { onFail(it) }
            })
        }
    }

    fun joinApp(
        password: String,
        confirmPassword: String,
        onComplete: () -> Unit,
        onFail: (message: String) -> Unit
    ) {
        useCases.joinUseCase(
            password,
            confirmPassword,
            {
                viewModelScope.launch(Dispatchers.Main) {
                    onComplete()
                }
            },
            {
                viewModelScope.launch(Dispatchers.Main) {
                    onFail(it)
                }
            }
        )
    }

    fun login(password: String, onSuccess: () -> Unit, onFail: (message: String) -> Unit) {
        if (password == this.password.value) {
            viewModelScope.launch(Dispatchers.Main) {
                onSuccess()
            }
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                onFail("Wrong Password")
            }
        }
    }


}