package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.StoryRepository
import com.dicoding.picodiploma.loginwithanimation.data.model.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import kotlinx.coroutines.launch

sealed interface StoryUiState<out T> {
    data class Success<T>(val data: T) : StoryUiState<T>
    data class Error(val errorMessage: String?) : StoryUiState<Nothing>
    object Loading : StoryUiState<Nothing>
}

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    private val _uiState = MutableLiveData<StoryUiState<List<ListStoryItem>>>()
    val uiState: LiveData<StoryUiState<List<ListStoryItem>>> = _uiState

    fun getUser(): LiveData<UserModel> {
        return repository.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

//    fun getStories() {
//        viewModelScope.launch {
//            _uiState.value = StoryUiState.Loading
//            try {
//                _uiState.value = StoryUiState.Success(repository.getStories().listStory)
//            } catch (e: HttpException) {
//                val errorBody =
//                    Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
//                _uiState.value = StoryUiState.Error(errorBody.message)
//            }
//        }
//    }

    val stories: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

}