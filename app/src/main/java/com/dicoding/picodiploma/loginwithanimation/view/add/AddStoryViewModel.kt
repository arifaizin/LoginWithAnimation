package com.dicoding.picodiploma.loginwithanimation.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.model.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.model.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.view.main.StoryUiState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {
    private val _uiState = MutableLiveData<StoryUiState<FileUploadResponse>>()
    val uiState: LiveData<StoryUiState<FileUploadResponse>> = _uiState

    fun uploadImage(file: File, description: String) {
        viewModelScope.launch {
            _uiState.value = StoryUiState.Loading
            try {
                _uiState.value = StoryUiState.Success(repository.uploadImage(file, description))
            } catch (e: HttpException) {
                val errorBody =
                    Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
                _uiState.value = StoryUiState.Error(errorBody.message)
            }
        }
    }

    fun saveUser(user: UserModel) {
        viewModelScope.launch {
            repository.saveUser(user)
        }
    }
}