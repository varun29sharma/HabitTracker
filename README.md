# 🎯 HabitTracker

> **Build Better Habits, Track Your Progress, Transform Your Life** 🚀

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/status-active-green.svg)](https://github.com/varun29sharma/HabitTracker)

---

## 📋 Table of Contents

- [✨ Features](#-features)
- [🛠️ Installation](#️-installation)
- [🚀 Quick Start](#-quick-start)
- [📚 Usage](#-usage)
- [🏗️ Architecture](#️-architecture)
- [🤝 Contributing](#-contributing)
- [📝 License](#-license)

---

## ✨ Features

- 📌 **Create & Manage Habits** - Add unlimited habits to track
- 📊 **Progress Tracking** - Visualize your daily progress with beautiful charts
- 🎨 **Customizable Categories** - Organize habits by category
- 📱 **User-Friendly Interface** - Clean, intuitive, and responsive design
- 💾 **Data Persistence** - All your data is safely stored locally
- 🔔 **Daily Reminders** - Get notified to complete your habits
- 📈 **Statistics & Analytics** - View detailed insights about your progress
- 🎯 **Goal Setting** - Set targets and track completion rates
- 🌙 **Dark Mode Support** - Comfortable tracking anytime, anywhere

---

## 🛠️ Installation

### Prerequisites
- **Node.js** v14 or higher
- **npm** or **yarn**

### Steps

```bash
# 1️⃣ Clone the repository
git clone https://github.com/varun29sharma/HabitTracker.git

# 2️⃣ Navigate to project directory
cd HabitTracker

# 3️⃣ Install dependencies
npm install

# 4️⃣ Start the application
npm start
```

---

## 🚀 Quick Start

1. **Launch the app** 🎬
   ```bash
   npm start
   ```

2. **Create your first habit** ➕
   - Click the "New Habit" button
   - Enter habit name and select category
   - Set your daily goal

3. **Start tracking** ✅
   - Log your daily completion
   - Watch your streak grow
   - Celebrate your wins! 🎉

4. **Review progress** 📊
   - View analytics dashboard
   - Check completion statistics
   - Adjust habits as needed

---

## 📚 Usage

### Creating a Habit

```javascript
const habit = {
  name: "Morning Exercise",
  category: "Health",
  frequency: "daily",
  target: 30 // minutes
};
```

### Tracking Progress

```javascript
// Log a completed habit
habitTracker.logCompletion(habitId, date);

// Get habit statistics
habitTracker.getStats(habitId);
```

### Viewing Dashboard

Access the dashboard at `http://localhost:3000` to see:
- 📅 Daily overview
- 📈 Weekly/Monthly trends
- 🏆 Top performing habits
- 🔥 Current streaks

---

## 🏗️ Architecture

```
HabitTracker/
├── 📁 src/
│   ├── 📁 components/      # React components
│   ├── 📁 pages/           # Page components
│   ├── 📁 hooks/           # Custom React hooks
│   ├── 📁 services/        # API services
│   ├── 📁 utils/           # Utility functions
│   └── 📁 styles/          # CSS files
├── 📁 public/              # Static assets
├── 📄 package.json
├── 📄 .env.example
└── 📄 README.md
```

---

## 🤝 Contributing

We love contributions! Here's how you can help:

### Steps to Contribute

1. **🍴 Fork** the repository
2. **🌿 Create** a feature branch
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **✏️ Make** your changes
4. **📝 Commit** with clear messages
   ```bash
   git commit -m "✨ Add amazing feature"
   ```
5. **📤 Push** to your branch
   ```bash
   git push origin feature/amazing-feature
   ```
6. **🔄 Open** a Pull Request

### Contribution Guidelines

- 🧪 Write tests for new features
- 📖 Update documentation
- 🎨 Follow the existing code style
- 💬 Be respectful and constructive

---

## 📊 Project Statistics

| Metric | Value |
|--------|-------|
| 📦 Version | 1.0.0 |
| 🔧 Built With | React, Node.js, MongoDB |
| 📄 License | MIT |
| 👥 Contributors | Welcome! |

---

## 🙏 Support

If you find HabitTracker helpful, please consider:

- ⭐ Starring the repository
- 🐛 Reporting bugs and issues
- 💡 Suggesting new features
- 🔄 Sharing with others

---

## 📝 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 🎉 Let's Build Better Habits Together!

**Start your journey today** 🌟 and transform your life one habit at a time.

Questions? 💬 Open an issue or contact the maintainers.

---

<div align="center">

**Made with ❤️ by [varun29sharma](https://github.com/varun29sharma)**

**⭐ If you like this project, please give it a star! ⭐**

</div>