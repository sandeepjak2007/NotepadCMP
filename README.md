
# 📱 Compose Multiplatform CRUD App

This repository contains a **Compose Multiplatform (Android + iOS)** app that demonstrates:

- 🗄️ **SQLDelight** → Local DB CRUD operations  
- 🧭 **JetBrains Navigation Compose** → Navigation across screens  
- 📑 **PDF Viewing**  
  - Android → [Bouquet](https://github.com/GRizzi91/bouquet) (Compose PDF library)  
  - iOS → Native **PDFKit** using `expect/actual`  
- ⚡ **Koin** → Dependency Injection  

---

## 🚀 Tech Stack
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)  
- [SQLDelight](https://cashapp.github.io/sqldelight/)  
- [Koin](https://insert-koin.io/)  
- [JetBrains Navigation Compose](https://github.com/JetBrains/compose-multiplatform-core/tree/master/navigation)  
- [Bouquet PDF](https://github.com/GRizzi91/bouquet)  

---

## 📂 Project Structure
```

shared/      → Common code (DB, DI, Nav, UI, PDF expect/actual)
androidApp/  → Android app (Bouquet PDF, SQLDelight driver)
iosApp/      → iOS app (UIKit PDFKit, SQLDelight driver)

````

---

## 🗄️ Database Example (SQLDelight)
```sql
CREATE TABLE Notes (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  content TEXT
);
````

```kotlin
class NotesRepository(private val db: NotesQueries) {
    fun getAll() = db.selectAll().asFlow().mapToList()
    fun insert(title: String, content: String) = db.insertNote(title, content)
    fun delete(id: Long) = db.deleteNote(id)
}
```

---

## 🧭 Navigation Example

```kotlin
NavHost(navController, startDestination = "home") {
    composable("home") { HomeScreen(navController) }
    composable("add") { AddNoteScreen(navController) }
    composable("pdf") { PdfScreen(navController) }
}
```

---

## 📑 PDF Viewer (expect/actual)

**Shared**

```kotlin
expect class PdfViewer {
    @Composable fun Render(path: String)
}
```

**Android (Bouquet)**

```kotlin
actual class PdfViewer {
    @Composable actual fun Render(path: String) {
        BouquetPdfView(filePath = path, modifier = Modifier.fillMaxSize())
    }
}
```

**iOS (UIKit PDFKit)**

```kotlin
actual class PdfViewer {
    @Composable actual fun Render(path: String) {
        UIKitPdfView(path) // PDFKit-backed UIView
    }
}
```

## 🛠 Dependency Injection (Koin)

```kotlin
val appModule = module {
    single { NotesRepository(get()) }
    single { createDatabase(get()) }
}
fun initKoin() = startKoin { modules(appModule) }
```

## 🛠 Android Images
<p>
<img width="270" height="600" alt="No-Notes-Android" src="https://github.com/user-attachments/assets/7e368576-89c3-41e4-82e5-b4fdf1cd1844" />
<img width="270" height="600" alt="NoteList-Android" src="https://github.com/user-attachments/assets/58a0d548-cf32-4578-8b01-857aab560221" />
<img width="270" height="600" alt="PDFViewer-Android" src="https://github.com/user-attachments/assets/bcc36112-2639-4da8-83c0-c8fb5c047f8c" />
</p>
<p>
  <img width="270" height="600" alt="Screenshot_20250828_010124" src="https://github.com/user-attachments/assets/7c6d5280-7118-4f0b-998a-ff5f51b6b881" />
</p>


## 📄 License

This project is licensed under the **MIT License**.
