# 🗞️ Briefly

A modern Android news app that displays the latest headlines using the Guardian API. Built with Jetpack Compose, Kotlin, and Clean Architecture, the app supports both online and offline reading.

---

## 📱 Features

- 📰 List of news articles with title, image, and brief description  
- 🔍 Detail screen showing full article content  
- ⚠️ Graceful handling of loading and error states  
- 📴 Offline support for previously loaded articles  
- 🎨 Clean and responsive Material Design UI  

---

## 🛠️ Tech Stack

| Layer         | Technologies                                      |
|---------------|---------------------------------------------------|
| Language      | Kotlin                                            |
| Architecture  | MVVM + Clean Architecture                         |
| UI            | Jetpack Compose, Material Design guidelines       |
| Async         | Kotlin Coroutines with Flow                       |
| DI            | Hilt                                              |
| Networking    | Retrofit, OkHttp (with logging interceptor)       |
| Image Loading | Coil                                              |
| Persistence   | Room          |

---

## 📂 Project Structure

```
com.example.newsapp
├── data
│   ├── api         // Retrofit service
│   ├── model       // DTOs
│   └── repository  // Implementation
├── domain
│   ├── model       // Business entities
│   ├── repository  // Abstract contract
│   └── usecase     // Business logic
├── presentation
│   ├── newslist    // List screen UI + ViewModel
│   └── detail      // Detail screen UI + ViewModel
├── di              // Hilt modules
└── utils           // Mappers, extensions, etc.
```

## 🚀 Getting Started

1. **Clone the repository**
   ```
   git clone https://github.com/amy6/Briefly.git
   cd Briefly
2. **Insert your Guardian API key**

   Add the following to your local.properties file:
   ```
   API_KEY=your_api_key_here
4. **Build and Run**

   Open the project in Android Studio and run it on a device or emulator.
