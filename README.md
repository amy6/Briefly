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

## 📸 Screenshots



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

## 📖 Design Decisions

- **Jetpack Compose** was used for faster UI development and a reactive UI model.
- **Clean Architecture** helps separate concerns and makes the codebase more testable and maintainable.
- **Kotlin Coroutines and Flow** was used for background processing and to manage UI state clearly.

---

## 🧪 Testing

- Unit tests are written for ViewModel, Respository and UseCases.
- Used Mockito for mocking fakes 

---

## 🤖 Developer Tools & AI Collaboration

To enhance development efficiency and design quality, this project was supported by the following AI tools:

### ChatGPT (OpenAI)
Used for:
- Code architecture decisions and best practices (Clean Architecture layering)
- Reviewing ViewModel and UseCase logic
- Explaining and debugging Kotlin Coroutines and StateFlow behavior
- Creating unit test strategies and scenarios

### Gemini (via Android Studio)
Assisted with:
- Quick in-editor answers to Android-specific tooling or Gradle issues

### Claude (Anthropic)
Contributed to:
- UI/UX design suggestions
- Structuring a minimal and user-friendly detail screen

> This project came together faster and smoother with a little help from AI tools—great for bouncing off ideas, refining code, and keeping things moving.

---

## 🧠 Improvements

Planned or potential areas for future enhancement:

- [ ] Add pagination for endless scrolling in the news list
- [ ] Add search functionality
- [ ] Add support for bookmarks/favorites saved offline
- [ ] Add settings screen (e.g., change category, region, etc.)  
- [ ] Add shimmer placeholders while loading
- [ ] Integrate UI tests using Jetpack Compose testing APIs  

---

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
