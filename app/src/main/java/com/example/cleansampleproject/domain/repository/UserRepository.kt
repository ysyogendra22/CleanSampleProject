package com.example.cleansampleproject.domain.repository

import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<Resource<List<User>>>
    fun getUserById(id: Int): Flow<Resource<User>>
}
