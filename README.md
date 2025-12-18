# 🎯 Habit Tracker – Personal Productivity Application

<div align="center">

![Java](https://img.shields.io/badge/Java-11-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-17.0.6-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-3.8+-red?style=for-the-badge&logo=apache-maven)

**A modern JavaFX desktop application to build habits, manage daily tasks, journal your thoughts, and analyze productivity through visual insights.**

[Features](#-features) •
[Installation](#-installation) •
[Usage](#-usage-guide) •
[Project Structure](#-project-structure) •
[Future Scope](#-future-scope)

</div>

---

## 🎯 Overview

**Habit Tracker** is a feature-rich desktop productivity application designed to help users:

- Build and maintain positive habits  
- Manage daily tasks efficiently  
- Reflect through journaling  
- Track progress using analytics, charts, and heatmaps  

The application follows a **clean MVC architecture**, supports **light/dark themes**, and ensures **data persistence** using JSON files.

---

## ✨ Key Highlights

- ✨ Modern, minimal UI with JavaFX  
- 📊 Analytics dashboard with charts & heatmap  
- 🔄 Automatic streak calculation  
- 📝 Built-in journaling  
- 💾 Persistent data storage (JSON)  
- 🌙 Light / Dark theme toggle  

---

## ✨ Features

### 🏃 Habits Management
- Create custom habits (Daily / Weekly / Monthly)
- Weekly and monthly progress tracking
- Automatic current & longest streak calculation
- Activity heatmap visualization
- Delete habits anytime

### ✅ Task Management
- Add daily tasks
- Mark tasks as completed
- Optional task notes
- Daily auto-reset logic
- Completion statistics

### 📔 Journal
- Write daily journal entries
- Edit & delete entries
- View history with timestamps
- Keyword search support

### 📊 Analytics Dashboard
- Completion trend line charts
- Year-wide activity heatmap
- Most & least consistent habits
- Performance statistics

### 🎨 User Interface
- Light & Dark themes
- Clean, responsive layout
- Intuitive navigation
- Smooth transitions

---

## 🛠 Technologies Used

| Technology | Version | Purpose |
|---------|---------|---------|
| **Java** | 11 | Core programming language |
| **JavaFX** | 17.0.6 | Desktop UI framework |
| **Maven** | 3.8+ | Build & dependency management |
| **Gson** | 2.10.1 | JSON serialization |
| **FXML** | – | UI layout |
| **CSS** | – | Styling & theming |

---

## 📁 Project Structure

HabitTracker/
│
├── src/main/java/com/habittracker/
│ ├── app/ # JavaFX entry point & scene manager
│ ├── controllers/ # MVC controllers
│ ├── models/ # Data models
│ ├── services/ # Business logic layer
│ └── utils/ # Utility classes
│
├── src/main/resources/
│ ├── fxml/ # JavaFX layouts
│ ├── css/ # Light & dark themes
│ └── data/ # Default JSON data
│
├── data/ # Runtime user data
├── pom.xml
└── README.md

yaml
Copy code

---

## 🧱 Architecture Overview

The application follows **MVC (Model–View–Controller)**:

- **Models** → Plain data objects  
- **Views** → JavaFX FXML layouts  
- **Controllers** → Handle user interactions  
- **Services** → Business logic & persistence  
- **Utils** → Helper classes  

This ensures clean separation of concerns and scalability.

---

## 📦 Prerequisites

- **Java JDK 11+**  
  ```bash
  java -version
Apache Maven 3.8+

bash
Copy code
mvn -version
Internet connection (for first Maven build)

🚀 Installation
1️⃣ Clone the Repository
bash
Copy code
git clone https://github.com/<your-username>/HabitTracker.git
cd HabitTracker
2️⃣ Build the Project
bash
Copy code
mvn clean compile
# or
mvn clean package
▶️ Running the Application
Option 1 – Using Maven (Recommended)
bash
Copy code
mvn javafx:run
Option 2 – Using Java Directly
bash
Copy code
java --module-path <path-to-javafx-sdk>/lib \
--add-modules javafx.controls,javafx.fxml \
-cp target/classes com.habittracker.app.AppLauncher
Option 3 – Run Packaged JAR
bash
Copy code
mvn clean package
java -jar target/habit-tracker-1.0-SNAPSHOT.jar
📖 Usage Guide
1️⃣ Login
Launch the app

Sign up with a username & password

Log in to access the dashboard

2️⃣ Dashboard
Overview of habits, tasks, and journals

Quick navigation to all modules

3️⃣ Habits
Add habits with frequency

Mark daily/weekly completion

Track streaks & heatmap activity

Delete habits

4️⃣ Tasks
Add daily tasks

Mark completed

Auto-reset next day

5️⃣ Journal
Write & save entries

Edit or delete past entries

View history

6️⃣ Analytics
View charts & heatmaps

Track consistency & trends

7️⃣ Theme Toggle
Switch between Light & Dark mode anytime

💼 Applications & Use Cases
Personal
Fitness, reading, meditation, coding habits

Daily planning & reflection

Professional
Skill development tracking

Work routine consistency

Educational
Study habit tracking

Learning journals

🔮 Future Scope
Analytics
 Export reports (PDF / CSV)

 Custom date-range analytics

Habits & Tasks
 Custom recurrence patterns

 Categories & tags

 Reminders & notifications

 Task priorities & subtasks

Journal
 Rich text editor

 Mood tracking

 Export journal

Data & Integrations
 Database (SQLite / PostgreSQL)

 Cloud sync & backup

 REST API

 Mobile / Web version

🤝 Contributing
Contributions are welcome!

bash
Copy code
git checkout -b feature/amazing-feature
git commit -m "Add amazing feature"
git push origin feature/amazing-feature
Open a Pull Request 🚀

👨‍💻 Author
Habit Tracker – JavaFX Desktop Application

GitHub: https://github.com/<your-username>/HabitTracker

<div align="center">
Made with ❤️ using Java & JavaFX

⭐ If you like this project, consider starring the repository! ⭐

</div> ```
