# BeReal File Browser Android App

This is a a simple Android app to allow the management of files 

**Functionality provided**

- Navigate folder hierarchy
- View image files (full screen preview)
- Create a new folder
- Upload an image file from local phone storage (via the floating action button, bottom right)
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

- Add login functionality (removing hard coded credentials)
- Improve UI styling 
- Add further unit tests (in particular improve coverage of ViewModel tests and interactors)
- Add Jetpack Compose testing / UI testing with espresso
- Improve error handling (use sealed classes to return more information about failures)
- Add caching layer to repositories to reduce network traffic and improve performance

**Screenshots**

*Root folder*

![Screenshot_1665993179](https://user-images.githubusercontent.com/2723637/196122225-3bb9a17f-1b75-4301-ad2b-d18171c57fec.png)

*Browsing into a folder*

![Screenshot_1665993176](https://user-images.githubusercontent.com/2723637/196122382-c961b5c4-b529-4c83-a147-67d425f8b63f.png)

*Viewing a file*

![Screenshot_1665993171](https://user-images.githubusercontent.com/2723637/196122551-d1929d81-16c5-4c4e-99bc-9f0a83a4ee43.png)

*Viewing a file (landscape) *

![Screenshot_1665993159](https://user-images.githubusercontent.com/2723637/196122617-7b6a1335-7c63-4829-a8f5-2b77de63c64a.png)

*Creating a folder*

![Screenshot_1665993187](https://user-images.githubusercontent.com/2723637/196122737-5a15c49f-9ac5-4574-a608-827df077adb6.png)

*Deleting a file (long press)*

![Screenshot_1665993205](https://user-images.githubusercontent.com/2723637/196122865-55363272-a9c3-4c55-9306-22b38ff2456f.png)

*Deleting a folder (long press)*

![Screenshot_1665993210](https://user-images.githubusercontent.com/2723637/196122984-4c76c3ef-a41a-4f44-949f-c3d77602a06b.png)


