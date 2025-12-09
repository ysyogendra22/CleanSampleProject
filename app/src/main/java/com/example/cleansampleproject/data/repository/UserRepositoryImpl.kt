package com.example.cleansampleproject.data.repository

import com.example.cleansampleproject.data.remote.api.UserApiService
import com.example.cleansampleproject.data.remote.dto.toDomainModel
import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {

    override fun getUsers(): Flow<Resource<List<User>>> = flow {
        try {
            emit(Resource.Loading)
            val response = apiService.getUsers()
            val users = response.users.map { it.toDomainModel() }
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }

    override fun getUserById(id: Int): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading)
            val userDto = apiService.getUserById(id)
            emit(Resource.Success(userDto.toDomainModel()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        }
    }
}
