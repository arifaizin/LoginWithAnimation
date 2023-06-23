package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.model.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.model.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface LoginUiState {
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    data class Error(val errorMessage: String?) : LoginUiState
    object Loading : LoginUiState
}

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                _uiState.value = LoginUiState.Success(repository.login(email, password))
            } catch (e: HttpException) {
                val errorBody =
                    Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
                _uiState.value = LoginUiState.Error(errorBody.message)
            }
        }
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}