Recipe_App

A modern Android application designed for recipe management and discovery. This project utilizes the latest Android development standards, including Jetpack Compose for a declarative UI and Kotlin DSL for build configurations.

📱 Features

🔐 Secure Authentication: Integrated with Firebase Auth to provide a seamless login and sign-up experience. Supports email/password authentication and ensures user data is protected across sessions.

🎲 Random Meal of the Day: Stuck on what to cook? The app features a "Meal of the Day" generator that fetches a random recipe from the database or API, providing instant inspiration with high-quality imagery and full cooking details.

📂 Categorized Browsing: Discover recipes organized by culinary categories (e.g., Seafood, Vegan, Desserts, Beef). The interface uses smooth transitions to let users dive deep into specific cuisines.

🔍 Intelligent Search: Quickly find specific dishes by typing names or main ingredients. The search is optimized for speed and provides real-time results.

📖 Comprehensive Recipe Details: Each recipe provides a holistic view, including:

Ingredients List: Measured and formatted for easy reading.

Step-by-Step Instructions: Clear, guided paths from prep to plate.

Visual Aids: High-resolution thumbnails to help you visualize the final result.

✨ Modern UI/UX: Built entirely with Jetpack Compose, featuring fluid animations, material design components, and a responsive layout that adapts to different screen sizes.

🛠 Tech Stack

Language: Kotlin

UI Framework: Jetpack Compose

Backend & Auth: Firebase Authentication

Build Tool: Gradle (Kotlin DSL)

Plugins:

com.android.application

org.jetbrains.kotlin.plugin.compose

com.google.devtools.ksp (Kotlin Symbol Processing)

com.google.gms.google-services

📂 Project Structure

Recipe_App/
├── app/                  # Main application module (Auth logic, UI, ViewModels)
├── gradle/               # Gradle wrapper and version catalog
├── build.gradle.kts      # Top-level build configuration
├── settings.gradle.kts   # Project and repository settings
└── gradle.properties     # JVM and build performance settings


🚀 Getting Started

Prerequisites

Android Studio: Ladybug or newer recommended.

JDK: The project uses the Foojay resolver to automatically manage the correct JDK version.

Firebase Project: You will need a google-services.json file from your Firebase console to enable authentication.

Installation

Clone the repository

git clone [https://github.com/0MohamedAshraf/Recipe_App.git](https://github.com/0MohamedAshraf/Recipe_App.git)


Add Firebase Configuration

Place your google-services.json in the app/ directory.

Open the project

Open Android Studio.

Select File > Open and navigate to the Recipe_App folder.

Build and Run

Let Gradle sync complete.

Connect an emulator or physical device.

Click the Run button (green play icon).

⚙️ Configuration

Firebase: Authentication is managed via the com.google.gms.google-services plugin. Ensure your SHA-1 fingerprint is registered in the Firebase Console for production builds.

Memory Settings: Optimized for performance with org.gradle.jvmargs=-Xmx2048m in gradle.properties.

Environment: Ensure your local.properties file points to your local Android SDK location.