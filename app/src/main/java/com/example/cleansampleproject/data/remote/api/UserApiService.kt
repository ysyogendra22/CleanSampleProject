package com.example.cleansampleproject.data.remote.api

import com.example.cleansampleproject.data.remote.dto.UserDto
import com.example.cleansampleproject.data.remote.dto.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {
    @GET("users")
    suspend fun getUsers(): UsersResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto
}
