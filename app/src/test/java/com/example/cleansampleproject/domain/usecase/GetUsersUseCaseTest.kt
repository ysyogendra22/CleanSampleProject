package com.example.cleansampleproject.domain.usecase

import com.example.cleansampleproject.domain.model.Address
import com.example.cleansampleproject.domain.model.Company
import com.example.cleansampleproject.domain.model.Resource
import com.example.cleansampleproject.domain.model.User
import com.example.cleansampleproject.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setup() {
        userRepository = mockk()
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `invoke should return loading state followed by success with users`() = runTest {
        // Given
        val mockUsers = listOf(
            User(
                id = 1,
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                phone = "+1234567890",
                age = 30,
                image = "https://example.com/image.jpg",
                address = Address(
                    address = "123 Main St",
                    city = "New York",
                    state = "NY",
                    postalCode = "10001"
                ),
                company = Company(
                    name = "Tech Corp",
                    title = "Software Engineer",
                    department = "Engineering"
                )
            ),
            User(
                id = 2,
                firstName = "Jane",
                lastName = "Smith",
                email = "jane.smith@example.com",
                phone = "+0987654321",
                age = 28,
                image = "https://example.com/image2.jpg",
                address = Address(
                    address = "456 Oak Ave",
                    city = "Los Angeles",
                    state = "CA",
                    postalCode = "90001"
                ),
                company = Company(
                    name = "Design Inc",
                    title = "UX Designer",
                    department = "Design"
                )
            )
        )

        coEvery { userRepository.getUsers() } returns flowOf(
            Resource.Loading,
            Resource.Success(mockUsers)
        )

        // When
        val results = getUsersUseCase().toList()

        // Then
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertEquals(mockUsers, (results[1] as Resource.Success).data)
    }

    @Test
    fun `invoke should return loading state followed by error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { userRepository.getUsers() } returns flowOf(
            Resource.Loading,
            Resource.Error(errorMessage)
        )

        // When
        val results = getUsersUseCase().toList()

        // Then
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Error)
        assertEquals(errorMessage, (results[1] as Resource.Error).message)
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Given
        coEvery { userRepository.getUsers() } returns flowOf(
            Resource.Loading,
            Resource.Success(emptyList())
        )

        // When
        val results = getUsersUseCase().toList()

        // Then
        assertEquals(2, results.size)
        assertTrue(results[0] is Resource.Loading)
        assertTrue(results[1] is Resource.Success)
        assertTrue((results[1] as Resource.Success).data.isEmpty())
    }
}
