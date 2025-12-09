package com.example.cleansampleproject.domain.model

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val age: Int,
    val image: String,
    val address: Address,
    val company: Company
) {
    val fullName: String get() = "$firstName $lastName"
}

data class Address(
    val address: String,
    val city: String,
    val state: String,
    val postalCode: String
)

data class Company(
    val name: String,
    val title: String,
    val department: String
)
