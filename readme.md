# BeReal File Browser App

This is a a simple Android app to allow the management of files 

**Functionality provided**

- Navigate folder hierarchy
- View image files (full screen preview)
- Create a new folder
- Upload an image file from local phone stroage (via the floating action button, bottom right)
- Delete a file or folder (via long press interaction)
- Supports landscape and portrait mode (and handles rotations)
- Progress indicator when loading content (file/directory list or file content)

**Built using:**

- Jetpack Compose (for UI) 
- MVVM / View Models
- Hilt (Dependency Injection)
- Coroutines
- Retrofit2 (API HTTP calls)
- Basic Auth http interceptor 
- Kotlinx serialization (JSON serialisation)
- Unit testing with JUnit, Mockk, Truth, Kotlinx coroutines testing

**Possible future improvements / ToDos**

- Add login functionality (removing hard code credentials)
- Improve UI styling 
- Add further unit tests (in particular improve coverage of ViewModel tests and interactors)
- Add Jetpack Compose testing / UI testing with espresso
- Improve error handling (use sealed classes to return more information about failures)
- Add caching layer to repositories to reduce network traffic and improve performance

**Screenshots**

