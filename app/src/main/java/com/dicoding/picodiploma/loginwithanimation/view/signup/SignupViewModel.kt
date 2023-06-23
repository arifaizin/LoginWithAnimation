package com.dicoding.picodiploma.loginwithanimation.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.model.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface RegisterUiState {
    data class Success(val message: String?) : RegisterUiState
    data class Error(val errorMessage: String?) : RegisterUiState
    object Loading : RegisterUiState
}

class SignupViewModel(private val repository: StoryRepository) : ViewModel() {

    private val _uiState = MutableLiveData<RegisterUiState>()
    val uiState: LiveData<RegisterUiState> = _uiState

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            try {
                _uiState.value =
                    RegisterUiState.Success(repository.register(name, email, password).message)
            } catch (e: HttpException) {
                val errorBody = Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
                _uiState.value = RegisterUiState.Error(errorBody.message)
            }
        }
    }
}