# Drag and Drop with Compose

![Kotlin](https://img.shields.io/badge/Kotlin-1.5.31-blueviolet)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.1.0-green)
![Hilt](https://img.shields.io/badge/Hilt-2.38.1-yellow)
![Room](https://img.shields.io/badge/Room-2.3.0-orange)
![Material Design](https://img.shields.io/badge/Material%20Design-3.0.0-blue)

## Overview

This project is a simple To-Do list application built with Kotlin using Jetpack Compose for the UI, Hilt for dependency injection, and Room for local data storage. The main feature of this application is the ability to drag and drop tasks to reorder them within the list.

## Features

- **Add Tasks**: Add new tasks to your to-do list.
- **Delete Tasks**: Remove tasks from your to-do list.
- **Update Tasks**: Edit existing tasks.
- **Drag and Drop**: Reorder tasks by dragging and dropping them within the list.
- **Local Storage**: Persist tasks using Room database.
- **Dependency Injection**: Manage dependencies using Hilt.
- **Material Design**: Modern UI design using Material Design components.

## Screenshots

![Screen_Recording_20240625_162657_DragAndDropWithCompose](https://github.com/LaurentVrevin/DragAndDropWithCompose/assets/94620399/81c38a88-a554-4403-9176-92b25a65530b)


## Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or later
- Kotlin 1.5.31 or later

### Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/LaurentVrevin/DragAndDropWithCompose.git
    cd DragAndDropWithCompose
    ```

2. **Open the project in Android Studio:**
    - Launch Android Studio and select `Open an existing project`.
    - Navigate to the project directory and open it.

3. **Build and run the project:**
    - Ensure your Android device or emulator is running.
    - Click on the `Run` button in Android Studio.

## Usage

### Adding a Task

1. Click on the floating action button (`+`) to navigate to the add task screen.
2. Enter the task details (title and description).
3. Click on the `Add` button to save the task and return to the main screen.

### Deleting a Task

*Note: The delete function is not yet implemented.*
1. Long-press on a task to enter selection mode.
2. Click on the delete icon to remove the selected task.

### Reordering Tasks

1. Long-press on a task until it becomes draggable.
2. Drag the task to the desired position and release it to drop.

## Dependencies

- **Jetpack Compose**: For building the UI.
- **Hilt**: For dependency injection.
- **Room**: For local database storage.
- **Material Design**: For UI components.

## Contact

Laurent Vrevin - [laurent.vrevin@hotmail.fr](laurent.vrevin@hotmail.fr)

LinkedIn: [https://www.linkedin.com/in/laurentvrevin](https://www.linkedin.com/in/laurentvrevin)

Project Link: [https://github.com/LaurentVrevin/DragAndDropWithCompose](https://github.com/LaurentVrevin/DragAndDropWithCompose)
