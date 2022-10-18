# Your Internet CookBook
- The app allows users to view recipes, and add them to a list of favorites for later use.
- A swipe to delete option is enabled in the favorites screen

## Architecture
- MVVM - Model-View-ViewModel.
- The application also follows the single activity architecture, with multiple fragments with their respective viewmodels, which in turn communicate with their respective repositories to fetch data.

## Tech Stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Glide](https://github.com/bumptech/glide) - an image-loading library.
    * [Retrofit](https://square.github.io/retrofit/) - to make network calls.
    * [Android KTX](https://developer.android.com/kotlin/ktx.html) - Provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    * [AndroidX](https://developer.android.com/jetpack/androidx) - Major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.

    * [Jetpack](https://developer.android.com/jetpack)🚀
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library that provides an abstraction layer over SQLite.
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - is an observable data holder.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.

## Get started
- Clone the repository
- Wait for the IDE to download all dependencies.
- Tinker with the application as you please.


## Screenshots
![Meal Categories](Images/categories.jpg)
![Meals under categories](Images/mealsundercategories.jpg)
![Meal Details](Images/mealdetails.jpg)
![Search](Images/search.jpg)
![Favorites](Images/db.jpg)


## Upcoming Features
- Improving the UI
- Firebase authentication.


### Contacts
- You can reach me via kelvinian87@gmail.com.
