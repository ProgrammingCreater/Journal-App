# Journal Application

## Overview
The Journal Application is a **Java Swing-based** program that allows users to create, save, and view journal entries. Entries are saved by date and organized by year. The application provides a simple and intuitive interface for managing personal journal entries.

## Features
- **Create and Save Entries**: Write journal entries and save them with the current date.
- **View Past Entries**: Browse entries by selecting specific years.
- **Organized Storage**: Entries are automatically organized into directories by year.
- **User-Friendly Interface**: Clean and responsive GUI with intuitive navigation.

## How to Use

### 1. Writing an Entry
- Open the application.
- Type your journal entry in the text area.
- Click the **"Save Entry"** button to save the entry with the current date.

### 2. Viewing Past Entries
- Click the **"View Past Entries"** button.
- Select one or more years from the list.
- Click **"Submit"** to display all entries from the selected years.

### 3. Returning Home
- Click the **"Home"** button to return to the main screen at any time.

## File Structure
- Entries are saved as `.txt` files in directories named after the year (e.g., `2025/2025-07-02.txt`).
- The application automatically creates directories for new years if they do not exist.

## Technical Details
- **Language**: Java  
- **Libraries**: Java Swing (GUI), Java IO (File operations)
- **Classes**:
  - `JournalApp`: Main application window and user interface
  - `FileManager`: Handles file operations (creating, reading, writing)

## Requirements
- Java Runtime Environment (JRE) 8 or later

## Installation

1. Ensure Java is installed on your system.
2. Compile the Java files:
   ```bash
   javac CompSciIA/JournalApp.java CompSciIA/FileManager.java
   ```

3. Run the application:

   ```bash
   java CompSciIA.JournalApp
   ```

## Notes

* The application is designed for simplicity and ease of use.
* All entries are stored locally in the same directory as the application.

## License

This project is open-source and free to use. Modify and distribute as needed.
