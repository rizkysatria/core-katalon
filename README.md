# Katalon Core Library

Core automation library berbentuk **JAR** yang digunakan bersama **Katalon Studio** untuk menyederhanakan, menstandarkan, dan men-scale automation test (Mobile / Web / API).

Library ini berisi **core logic**, **utils**, dan **abstraction layer** yang reusable dan **parallel-safe**.

---

## 📦 Project Structure

```text
.
├── src/
│   └── main/
│       └── groovy/
│           ├── core/          # Core abstraction (driver, platform, context)
│           ├── utils/         # Reusable utilities (scroll, wait, input, etc)
│           ├── locator/       # Locator models / adapters
│           └── config/        # Environment & execution config
├── libs/                      # Output JAR (copied here after build)
├── build/                     # Gradle build output
├── build.gradle               # Gradle build configuration
├── gradle/
├── gradlew
└── gradlew.bat


BUILD : 
macos : ./gradlew clean build
windows : gradlew.bat clean build

Output JAR : build/libs/
