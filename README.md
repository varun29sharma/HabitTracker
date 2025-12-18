# HabitTracker

A comprehensive Java/JavaFX desktop application for tracking and managing daily habits. Built with Maven, this project provides an intuitive GUI for users to monitor their habit progress and maintain accountability.

## Project Overview

HabitTracker is a modern desktop application that helps users build and track positive habits. The application features a clean, user-friendly interface built with JavaFX and follows enterprise-level architecture patterns with well-organized packages and separation of concerns.

## Technology Stack

- **Language**: Java
- **UI Framework**: JavaFX
- **Build Tool**: Maven
- **Architecture**: MVC (Model-View-Controller) with Service and Repository layers

## Project Structure

```
HabitTracker/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/habittracker/
│   │   │       ├── app/
│   │   │       │   └── Application entry point and main app class
│   │   │       ├── controller/
│   │   │       │   └── JavaFX Controller classes for UI logic
│   │   │       ├── model/
│   │   │       │   └── Domain models and data classes
│   │   │       ├── service/
│   │   │       │   └── Business logic and service layer
│   │   │       ├── repository/
│   │   │       │   └── Data access layer and persistence
│   │   │       └── util/
│   │   │           └── Utility classes and helper functions
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       │   └── UI layout files (*.fxml)
│   │       ├── css/
│   │       │   └── Stylesheets (*.css)
│   │       ├── data/
│   │       │   └── Data files and configurations
│   │       └── images/
│   │           └── Application icons and images
│   │
│   └── test/
│       └── Java unit tests
│
├── pom.xml
├── README.md
└── .gitignore
```

## Package Organization

### `com.habittracker.app`
- **Purpose**: Application entry point and main application class
- **Contents**: Main application launcher, initialization code, and stage management

### `com.habittracker.controller`
- **Purpose**: JavaFX controller classes for UI logic
- **Contents**: FXML controllers that handle user interactions and update the view
- **Responsibility**: Binding UI events to business logic, updating UI elements

### `com.habittracker.model`
- **Purpose**: Domain models and data transfer objects
- **Contents**: Classes representing entities like Habit, HabitLog, User, etc.
- **Responsibility**: Encapsulating data structures and business entities

### `com.habittracker.service`
- **Purpose**: Business logic layer
- **Contents**: Service classes implementing core application logic
- **Responsibility**: Orchestrating operations between controllers and repositories, enforcing business rules

### `com.habittracker.repository`
- **Purpose**: Data access layer
- **Contents**: Repository classes for database operations and persistence
- **Responsibility**: CRUD operations, database queries, data persistence

### `com.habittracker.util`
- **Purpose**: Utility classes and helper functions
- **Contents**: Date/time utilities, validation helpers, formatting functions, constants
- **Responsibility**: Providing reusable functionality across the application

## Resources Structure

### `src/main/resources/fxml/`
- FXML layout files defining UI structures
- Organized by feature/screen (e.g., `main-view.fxml`, `habit-details-view.fxml`)
- Referenced by controller classes

### `src/main/resources/css/`
- CSS stylesheets for application theming
- Style definitions for JavaFX components
- Global and component-specific styles

### `src/main/resources/data/`
- Configuration files
- Sample data files
- Application properties

### `src/main/resources/images/`
- Application icons
- UI images and assets
- Image resources referenced in FXML files

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Apache Maven 3.6 or higher

### Building the Project

```bash
# Clone the repository
git clone https://github.com/varun29sharma/HabitTracker.git
cd HabitTracker

# Build with Maven
mvn clean install

# Run the application
mvn javafx:run
```

### Running Tests

```bash
mvn test
```

## Architecture Highlights

### Separation of Concerns
- **Controllers** handle UI events without business logic
- **Services** manage business operations and rules
- **Repositories** handle all data access
- **Models** represent domain entities

### Layered Architecture
1. **Presentation Layer**: JavaFX Controllers and FXML views
2. **Service Layer**: Business logic and use cases
3. **Data Layer**: Repository pattern for data access
4. **Utilities**: Shared helper functions and constants

## Features

- Create and manage daily habits
- Track habit completion status
- View habit statistics and progress
- Set habit goals and reminders
- Manage habit categories
- Visual progress indicators

## Configuration

Application configuration can be adjusted in:
- `src/main/resources/data/application.properties`
- Controller initialization parameters
- FXML stage dimensions

## Contributing

Contributions are welcome! Please follow these guidelines:
1. Create a feature branch from `main`
2. Make your changes in the appropriate package
3. Write unit tests for new features
4. Submit a pull request with a clear description

## Development Guidelines

### Package Responsibilities
- Only add code to the package where it logically belongs
- Keep controllers thin—move business logic to services
- Services should not depend on controllers or UI classes
- Use repositories for all data access operations

### Naming Conventions
- Controllers: `*Controller` suffix
- Services: `*Service` suffix
- Repositories: `*Repository` suffix
- Models: Business entity names without suffix
- FXML files: lowercase with hyphens (e.g., `habit-view.fxml`)

### Code Organization
- Keep classes focused and single-purpose
- Use dependency injection where applicable
- Document public methods with JavaDoc
- Maintain consistent code formatting

## Troubleshooting

### JavaFX Module Issues
If encountering JavaFX-related errors, ensure:
- FXML files are in `src/main/resources/fxml/`
- CSS files are properly referenced in FXML
- Module paths are correctly configured in `pom.xml`

### Build Issues
```bash
# Clean build if encountering issues
mvn clean install -DskipTests

# Rebuild specific module
mvn clean package -pl :HabitTracker
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact & Support

For issues, questions, or suggestions, please open an issue in the GitHub repository or contact the maintainer.

---

**Last Updated**: 2025-12-18

