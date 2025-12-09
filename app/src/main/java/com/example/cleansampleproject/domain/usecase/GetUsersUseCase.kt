package com.example.cleansampleproject.domain.usecase

import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> {
        return repository.getUsers()
    }
}
