package com.example.cleansampleproject.presentation.viewmodel

import app.cash.turbine.test
import com.example.cleansampleproject.domain.model.Address
import com.example.cleansampleproject.domain.model.Company
import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var viewModel: UserListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getUsersUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading when users are being fetched`() = runTest {
        // Given
        val mockUsers = listOf(
            createMockUser(1, "John", "Doe"),
            createMockUser(2, "Jane", "Smith")
        )

        coEvery { getUsersUseCase() } returns flowOf(
            Resource.Loading,
            Resource.Success(mockUsers)
        )

        // When
        viewModel = UserListViewModel(getUsersUseCase)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)
            assertNull(loadingState.error)
            assertTrue(loadingState.users.isEmpty())

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertNull(successState.error)
            assertEquals(2, successState.users.size)
            assertEquals("John", successState.users[0].firstName)
            assertEquals("Jane", successState.users[1].firstName)
        }
    }

    @Test
    fun `state should show error when use case returns error`() = runTest {
        // Given
        val errorMessage = "Failed to fetch users"
        coEvery { getUsersUseCase() } returns flowOf(
            Resource.Loading,
            Resource.Error(errorMessage)
        )

        // When
        viewModel = UserListViewModel(getUsersUseCase)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val errorState = awaitItem()
            assertFalse(errorState.isLoading)
            assertEquals(errorMessage, errorState.error)
            assertTrue(errorState.users.isEmpty())
        }
    }

    @Test
    fun `getUsers should update state with new data`() = runTest {
        // Given
        val initialUsers = listOf(createMockUser(1, "John", "Doe"))
        val newUsers = listOf(
            createMockUser(1, "John", "Doe"),
            createMockUser(2, "Jane", "Smith"),
            createMockUser(3, "Bob", "Johnson")
        )

        coEvery { getUsersUseCase() } returnsMany listOf(
            flowOf(Resource.Loading, Resource.Success(initialUsers)),
            flowOf(Resource.Loading, Resource.Success(newUsers))
        )

        viewModel = UserListViewModel(getUsersUseCase)

        // When
        viewModel.state.test {
            skipItems(2) // Skip initial loading and success states

            viewModel.getUsers()

            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertEquals(3, successState.users.size)
        }
    }

    @Test
    fun `state should handle empty user list`() = runTest {
        // Given
        coEvery { getUsersUseCase() } returns flowOf(
            Resource.Loading,
            Resource.Success(emptyList())
        )

        // When
        viewModel = UserListViewModel(getUsersUseCase)

        // Then
        viewModel.state.test {
            val loadingState = awaitItem()
            assertTrue(loadingState.isLoading)

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertNull(successState.error)
            assertTrue(successState.users.isEmpty())
        }
    }

    private fun createMockUser(id: Int, firstName: String, lastName: String): User {
        return User(
            id = id,
            firstName = firstName,
            lastName = lastName,
            email = "$firstName.$lastName@example.com".lowercase(),
            phone = "+1234567890",
            age = 30,
            image = "https://example.com/image$id.jpg",
            address = Address(
                address = "$id Main St",
                city = "New York",
                state = "NY",
                postalCode = "10001"
            ),
            company = Company(
                name = "Tech Corp",
                title = "Software Engineer",
                department = "Engineering"
            )
        )
    }
}
