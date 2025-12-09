package com.example.cleansampleproject.data.remote.dto

import com.example.cleansampleproject.domain.model.Address
import com.example.cleansampleproject.domain.model.Company
import com.example.cleansampleproject.domain.model.User
import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("users") val users: List<UserDto>,
    @SerializedName("total") val total: Int,
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("age") val age: Int,
    @SerializedName("image") val image: String,
    @SerializedName("address") val address: AddressDto,
    @SerializedName("company") val company: CompanyDto
)

data class AddressDto(
    @SerializedName("address") val address: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("postalCode") val postalCode: String
)

data class CompanyDto(
    @SerializedName("name") val name: String,
    @SerializedName("title") val title: String,
    @SerializedName("department") val department: String
)

// Extension functions to map DTOs to domain models
fun UserDto.toDomainModel(): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        age = age,
        image = image,
        address = address.toDomainModel(),
        company = company.toDomainModel()
    )
}

fun AddressDto.toDomainModel(): Address {
    return Address(
        address = address,
        city = city,
        state = state,
        postalCode = postalCode
    )
}

fun CompanyDto.toDomainModel(): Company {
    return Company(
        name = name,
        title = title,
        department = department
    )
}
