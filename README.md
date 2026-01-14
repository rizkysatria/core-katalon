# Katalon Core Library

Core automation library berbentuk **JAR** yang digunakan bersama **Katalon Studio** untuk menyederhanakan, menstandarkan, dan men-scale automation test (Mobile / Web / API).

Library ini berisi **core logic**, **utils**, dan **abstraction layer** yang reusable dan **parallel-safe**.

---

## 📦 Project Structure

```text
core/
├── adapter/                
│   ├── android/
│   └── ios/
│
├── interaction/            
│
├── runtime/                
│   ├── locator/
│   ├── profile/
│   └── storage/
│
├── types/                  
│
├── ui/                     
│
├── utility/                
│
└── scaffold/               
    ├── structure/         
    └── gherkin/   


BUILD : 
macos : ./gradlew clean build
windows : gradlew.bat clean build

Output JAR : build/libs/
