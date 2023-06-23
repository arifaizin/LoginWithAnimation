package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.model.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.view.signup.UserUiState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uiState = MutableLiveData<UserUiState>()
    val uiState: LiveData<UserUiState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            try {
                _uiState.value =
                    UserUiState.Success(repository.login(email, password).message)
            } catch (e: HttpException) {
                val errorBody = Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
                _uiState.value = UserUiState.Error(errorBody.message)
            }
        }
    }
}