# HabitTracker

A powerful desktop application for tracking and managing your daily habits, built with Java and JavaFX.

## Overview

HabitTracker is a modern habit tracking application that helps you build and maintain positive habits. With an intuitive interface and robust tracking features, you can monitor your progress, set goals, and achieve personal growth.

## Features

- 📱 **Intuitive UI** - Clean, user-friendly interface built with JavaFX
- 📊 **Progress Tracking** - Visual progress indicators and statistics
- 📈 **Analytics** - Detailed habit completion analytics and trends
- 🎯 **Goal Setting** - Set daily, weekly, and monthly habit goals
- 💾 **Data Persistence** - Local data storage with file-based database
- 🔔 **Notifications** - Reminders for habit tracking
- 🎨 **Customizable** - Personalize your habit categories and themes

## Technology Stack

- **Language**: Java 11+
- **GUI Framework**: JavaFX
- **Build Tool**: Maven
- **Database**: Local file-based storage
- **IDE**: IntelliJ IDEA / Eclipse / NetBeans (recommended)

## Project Structure

```
HabitTracker/
├── pom.xml                          # Maven configuration
├── README.md                         # Project documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/habittracker/
│   │   │       ├── HabitTrackerApp.java      # Main application entry point
│   │   │       ├── controller/               # JavaFX controllers
│   │   │       ├── model/                    # Data models
│   │   │       ├── service/                  # Business logic
│   │   │       ├── repository/               # Data access layer
│   │   │       ├── util/                     # Utility classes
│   │   │       └── view/                     # Custom UI components
│   │   └── resources/
│   │       ├── fxml/                        # JavaFX FXML files
│   │       ├── css/                         # Stylesheets
│   │       ├── images/                      # Assets and icons
│   │       └── config/                      # Configuration files
│   └── test/
│       └── java/
│           └── com/habittracker/           # Unit and integration tests
├── target/                          # Build output directory
└── .gitignore                       # Git ignore configuration
```

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)** - Version 11 or higher
  - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://openjdk.java.net/)
- **Maven** - Version 3.6.0 or higher
  - Download from [Apache Maven](https://maven.apache.org/)
- **Git** - For version control
  - Download from [Git](https://git-scm.com/)

Verify installations:
```bash
java -version
javac -version
mvn -version
```

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/varun29sharma/HabitTracker.git
cd HabitTracker
```

### 2. Configure JavaFX (if not already included in pom.xml)

The `pom.xml` includes JavaFX dependencies. Ensure your `pom.xml` contains:

```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21.0.1</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21.0.1</version>
</dependency>
```

### 3. Build the Project

```bash
mvn clean install
```

This command will:
- Clean previous build artifacts
- Download all dependencies
- Compile the source code
- Run tests
- Package the application

### 4. Run the Application

#### Option A: Using Maven
```bash
mvn javafx:run
```

#### Option B: Using Java directly (after build)
```bash
java -jar target/HabitTracker-1.0-SNAPSHOT.jar
```

#### Option C: From IDE
- Open the project in your IDE (IntelliJ IDEA, Eclipse, or NetBeans)
- Right-click on `HabitTrackerApp.java`
- Select "Run 'HabitTrackerApp.main()'"

## Maven Build Configuration

The project uses Maven for dependency management and building. Key `pom.xml` elements:

```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <javafx.version>21.0.1</javafx.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
        </plugin>
        <plugin>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-maven-plugin</artifactId>
            <version>0.0.8</version>
        </plugin>
    </plugins>
</build>
```

## Usage

1. **Launch the Application** - Start HabitTracker using one of the methods above
2. **Create a Habit** - Add a new habit by clicking the "Add Habit" button
3. **Track Progress** - Mark habits as completed for each day
4. **View Analytics** - Check the analytics section for insights and trends
5. **Manage Habits** - Edit or delete habits as needed

## Platform Availability

### Desktop Platforms Supported

- **Windows** - Windows 10/11 (64-bit)
- **macOS** - macOS 10.13+ (Intel and Apple Silicon with JavaFX 17+)
- **Linux** - Ubuntu 18.04+, Debian, Fedora, and other Linux distributions

### Mobile Availability

Currently, HabitTracker is a **desktop application only**. For mobile availability:

- **Android/iOS**: Consider using complementary mobile apps or building a web service backend
- **Cross-Platform**: Future versions may include mobile support through technologies like:
  - Gluon Mobile (JavaFX for Android/iOS)
  - Native iOS/Android apps with shared backend
  - React Native or Flutter integration
  - Web-based interface with JavaFX as desktop client

## Development

### Running Tests

```bash
mvn test
```

### Building for Distribution

```bash
mvn clean package
```

This creates an executable JAR file in the `target/` directory.

### IDE Setup

#### IntelliJ IDEA
1. Open the project
2. Ensure SDK is set to Java 11+
3. Maven should auto-download dependencies
4. Run the project directly from the IDE

#### Eclipse
1. Import as Maven project
2. Right-click project → Configure → Convert to Faceted Form
3. Add JavaFX library to build path

#### NetBeans
1. Open as Maven project
2. Maven dependencies should auto-resolve
3. Run the project using Shift+F6

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Troubleshooting

### Issue: "JavaFX modules not found"
**Solution**: Ensure JavaFX dependencies are correctly configured in `pom.xml` and run `mvn clean install`

### Issue: "Cannot find main class"
**Solution**: Verify `HabitTrackerApp.java` has a proper `main()` method and is compiled

### Issue: "Build fails with Java version mismatch"
**Solution**: Check `pom.xml` Java version properties match your installed JDK version

### Issue: Module not found errors on macOS
**Solution**: Add JavaFX VM options: `--add-modules javafx.controls,javafx.fxml`

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**Varun Sharma** - [GitHub Profile](https://github.com/varun29sharma)

## Acknowledgments

- JavaFX community and documentation
- Maven plugins and tooling
- All contributors and users

## Support

For issues, feature requests, or contributions:
- Open an issue on [GitHub Issues](https://github.com/varun29sharma/HabitTracker/issues)
- Contact the project maintainer

---

**Last Updated**: December 18, 2025
