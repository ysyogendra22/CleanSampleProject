package com.example.cleansampleproject.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.usecase.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserDetailState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("userId")?.let { userId ->
            getUserById(userId.toInt())
        }
    }

    private fun getUserById(userId: Int) {
        viewModelScope.launch {
            getUserByIdUseCase(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.value = UserDetailState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = UserDetailState(user = resource.data)
                    }
                    is Resource.Error -> {
                        _state.value = UserDetailState(error = resource.message)
                    }
                }
            }
        }
    }
}
