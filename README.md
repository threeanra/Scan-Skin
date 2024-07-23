## Scan Skin
Scan Skin was originally one of the assignments from the Dicoding course “Belajar Penerapan Machine Learning untuk Android” called Asclepius. In the end it was made with a new name, Scan Skin which aimed at campus assignments.

## Screenshot
![Screenshoot1](https://github.com/user-attachments/assets/8e6875c9-9033-45e5-90de-a68a7155b16e)
![Screenshot2](https://github.com/user-attachments/assets/215742a6-4055-45d8-851b-eddbb6c7b021)

## Features
- Splash Screen
- Login
- Save User Login History To Data Store
- Article News Related to Cancer
- View Article
- View & Save History
- View Profile
- Skin Cancer Detection (thanks for Dicoding Team for the model of AI)

## How To Use it?
- Clone this repository to your local machine
- Check build.gradle.kts file
```bash 
buildConfigField("String", "API_KEY", "\"YOUR_API_KEY\"")
```
Replace "YOUR_API_KEY" with your key (if you don't have it, you can generate the key [here](https://newsapi.org/register))
- Run the Project

## Login Account
```json 
{
    "email"     : "test@gmail.com",
    "password"  : "test123"
}
```

## Build With
This Application is built with [MVVM (Model View View-Model)](https://developer.android.com/topic/architecture#recommended-app-arch) architecture, and several libraries

- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [CoordinatorLayout](https://developer.android.com/reference/androidx/coordinatorlayout/widget/CoordinatorLayout)
- [CircelImageView](https://github.com/hdodenhof/CircleImageView)
- [Glide](https://github.com/bumptech/glide)
- [Retrofit](https://square-github-io.translate.goog/retrofit/?_x_tr_sl=en&_x_tr_tl=id&_x_tr_hl=id&_x_tr_pto=tc)
- [Gson](https://github.com/square/retrofit/tree/trunk/retrofit-converters/gson)
- [OkHttp3](https://square.github.io/okhttp/recipes/)
- [Room](https://developer.android.com/training/data-storage/room)
- [AndroidKTX](https://developer.android.com/kotlin/ktx?hl=id)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Tensorflow Lite](https://www.tensorflow.org/lite/android)
