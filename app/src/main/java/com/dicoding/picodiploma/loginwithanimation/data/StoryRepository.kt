package com.dicoding.picodiploma.loginwithanimation.data

import com.dicoding.picodiploma.loginwithanimation.data.model.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.model.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.data.network.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    fun getUser(): Flow<UserModel> {
        return userPreference.getUser()
    }

    suspend fun saveUser(user: UserModel) {
        userPreference.saveUser(user)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, userPreference)
            }.also { instance = it }
    }
}