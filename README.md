# 🍽️ Recipe App (Jetpack Compose)

![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Room](https://img.shields.io/badge/Room_Database-3DDC84?style=for-the-badge&logo=android&logoColor=white)

A fully-featured, production-ready Android application built entirely with **Jetpack Compose**. This app allows users to explore global cuisines, search for specific recipes by ingredient or country, watch embedded video tutorials, and save their favorite meals offline. 

## 🌟 Comprehensive Feature List

### 🔐 Authentication & Profiles
* **Multi-Method Login:** Supports Email/Password registration and modern Google Sign-In using the Android **Credential Manager**.
* **Guest Mode:** Users can browse the app as a guest and are only prompted to create an account when attempting to save a favorite recipe.
* **Custom Profiles:** Users can upload a custom profile picture (saved to Firebase Cloud Storage) or choose from a built-in selection of colored avatars (saved locally via DataStore).

### 🏠 Discovery (Home)
* **Meal of the Day:** A dynamically fetched featured recipe highlighted at the top of the feed.
* **Trending & Categories:** Horizontal scrollable categories and a vertical feed of trending meals, utilizing Compose's `LazyRow` and `LazyColumn`.

### 🔍 Advanced Search Engine
* **Cross-Filtering:** Search for recipes via multiple tabs: **Categories** (e.g., Seafood), **Countries** (e.g., Mexican, Japanese), or **Ingredients** (e.g., Chicken, Avocado).
* **Live Search:** Real-time text filtering on the results page to quickly find specific meals within a chosen category.

### 📖 Recipe Details
* **Visual Ingredients Grid:** Displays exactly what ingredients are needed along with visual thumbnails and exact measurements.
* **Video Integration:** Natively embeds YouTube video tutorials using the `android-youtube-player` library.
* **Interactive UI:** Expandable/collapsible instruction cards with smooth rotation animations.

### 💾 Offline-First Favorites
* **Room Database Integration:** Saved recipes are cached locally. Users can view their favorite meals even when completely disconnected from the internet.
* **Safety Dialogs:** Custom alert dialogs prevent accidental deletion of favorite recipes.

### ⚙️ App Settings & UX
* **Dynamic Localization:** Instantly switch the app language between English and Arabic using the modern `AppCompatDelegate` (fully supports Right-to-Left layouts).
* **Dark/Light Theme:** Persistent theme toggling saved locally using Preferences DataStore.
* **Graceful Error Handling:** Custom `OfflineScreen` states catch Retrofit network exceptions and display a user-friendly "Try Again" UI instead of crashing.

---

## 🏗️ Architecture & Design Pattern

This project strictly adheres to the **MVVM (Model-View-ViewModel)** architecture and the **Repository Pattern** to enforce a separation of concerns and a Single Source of Truth.

* **UI Layer:** Built with Jetpack Compose. Screens observe state from ViewModels using `collectAsStateWithLifecycle()`.
* **ViewModel Layer:** Manages UI state using `MutableStateFlow` and handles business logic (e.g., wrapping network calls in Coroutine `try-catch` blocks).
* **Data Layer:** * *Remote:* Retrofit interfaces (`ApiService`) and remote data sources fetch JSON from TheMealDB.
  * *Local:* Room Database (`FavoriteDao`) caches saved recipes for offline access.

### 📁 Folder Structure
```text
com.example.recipe_app
│
├── components/          # Reusable Compose widgets (Headers, Dialogs, Loaders)
├── database/            # Room Database setup, Entities (Favorite), and DAOs
├── network/             # Retrofit client, ApiService, and RemoteDataSource
├── service/             # Firebase Authentication & Storage Implementation
├── ui.theme/            # Compose Theme, Colors, and Typography
│
└── screens/             # Feature-based modules
    ├── detailsScreen/   # Recipe details, Video Player, Ingredients Grid
    ├── favScreen/       # Offline Favorites UI & Room DB integration
    ├── homeScreen/      # Main dashboard, Categories, Trending feeds
    ├── searchScreen/    # Search Engine, Filters, Country/Ingredient lists
    ├── profileScreen/   # Avatar selection, Dark Mode toggle, Language Switcher
    ├── signInScreen/    # Credential Manager & Email login
    └── splashScreen/    # Lottie animations & Session checking
