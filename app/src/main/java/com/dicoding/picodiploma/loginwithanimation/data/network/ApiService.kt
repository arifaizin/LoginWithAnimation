package com.dicoding.picodiploma.loginwithanimation.data.network

import com.dicoding.picodiploma.loginwithanimation.data.model.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.model.RegisterResponse
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

}