package com.example.cleansampleproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserListState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserListState())
    val state: StateFlow<UserListState> = _state.asStateFlow()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = UserListState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = UserListState(users = resource.data)
                    }
                    is Resource.Error -> {
                        _state.value = UserListState(error = resource.message)
                    }
                }
            }
        }
    }
}
