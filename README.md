# Clean Sample Project

A modern Android application demonstrating Clean Architecture principles with MVVM pattern, Jetpack Compose, and Dependency Injection using Hilt.

## Project Description

This sample project showcases best practices in Android development by implementing a user management application. It fetches user data from a REST API and displays it in a clean, intuitive interface with support for viewing detailed user information.

## Architecture

The project follows **Clean Architecture** principles with three distinct layers:

### 1. Domain Layer
- **Models**: Pure business objects (`User`, `Address`, `Company`)
- **Use Cases**: Business logic encapsulation (`GetUsersUseCase`, `GetUserByIdUseCase`)
- **Repository Interface**: Abstract data operations

### 2. Data Layer
- **Repository Implementation**: Concrete data access logic
- **Remote Data Source**: API service using Retrofit
- **DTOs**: Data transfer objects for network responses

### 3. Presentation Layer
- **MVVM Pattern**: ViewModel + Compose UI
- **State Management**: StateFlow for reactive UI updates
- **Navigation**: Jetpack Navigation Compose

```
app/
├── data/
│   ├── remote/
│   │   ├── api/        # API service interfaces
│   │   └── dto/        # Data transfer objects
│   └── repository/     # Repository implementations
├── domain/
│   ├── model/          # Business models
│   ├── repository/     # Repository interfaces
│   └── usecase/        # Business logic
└── presentation/
    ├── navigation/     # Navigation setup
    ├── ui/
    │   └── screens/    # Compose screens
    └── viewmodel/      # ViewModels
```

## Tech Stack

### Core
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Coroutines & Flow**: Asynchronous programming and reactive streams

### Architecture & DI
- **Hilt**: Dependency injection framework
- **ViewModel**: Lifecycle-aware UI state management
- **Navigation Compose**: Type-safe navigation

### Networking
- **Retrofit**: HTTP client for REST API calls
- **OkHttp**: Network interceptor and logging
- **Gson**: JSON serialization/deserialization

### Image Loading
- **Coil**: Efficient image loading library for Compose

### Testing
- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Turbine**: Flow testing utilities
- **Coroutines Test**: Testing utilities for coroutines

## Features

- **Splash Screen**: Animated entry screen with 2-second delay
- **User List**: Displays users in a scrollable list with profile images
- **User Details**: Comprehensive view of user information including:
  - Personal details (name, email, phone, age)
  - Address information
  - Company details
- **Loading States**: Visual feedback during data fetching
- **Error Handling**: Graceful error display with retry functionality
- **Offline-First**: Resource wrapper for consistent state management

## How to Run

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with minimum API level 26

### Steps
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd CleanSampleProject
   ```

2. Open the project in Android Studio

3. Sync Gradle dependencies:
   - Click "Sync Now" when prompted
   - Or use `File > Sync Project with Gradle Files`

4. Run the application:
   - Select a device or emulator
   - Click the "Run" button or press `Shift + F10`

### Running Tests
```bash
# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests GetUsersUseCaseTest
./gradlew test --tests UserListViewModelTest
```

## Project Structure Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                    │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Screens    │→ │  ViewModels  │→ │  Navigation  │  │
│  │  (Compose)   │  │ (StateFlow)  │  │   (NavHost)  │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│                     Domain Layer                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │   Use Cases  │  │    Models    │  │  Repository  │  │
│  │  (Business   │  │   (Entities) │  │  (Interface) │  │
│  │    Logic)    │  │              │  │              │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└──────────────────────┬──────────────────────────────────┘
                       │
┌──────────────────────▼──────────────────────────────────┐
│                      Data Layer                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  │
│  │  Repository  │→ │  API Service │→ │     DTOs     │  │
│  │    (Impl)    │  │  (Retrofit)  │  │   (Network)  │  │
│  └──────────────┘  └──────────────┘  └──────────────┘  │
└─────────────────────────────────────────────────────────┘
```

## Key Concepts Demonstrated

- **Separation of Concerns**: Each layer has distinct responsibilities
- **Dependency Inversion**: High-level modules don't depend on low-level modules
- **Single Responsibility**: Each class has one reason to change
- **Testability**: Business logic isolated from framework dependencies
- **Reactive Programming**: Flow-based state management
- **Modern Android Development**: Latest Jetpack libraries and best practices

## License

This is a sample project for educational purposes.
