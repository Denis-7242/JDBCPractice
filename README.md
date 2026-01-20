# 🎓 JDBC Student Manager

A robust, lightweight console application for managing student records using **Java** and **JDBC**. This project serves as a practical implementation of **CRUD** (Create, Read, Update, Delete) operations with a MySQL database, emphasizing secure configuration practices.

---

## 🚀 Features

*   **🔌 Secure Connection**: Establishes a connection to MySQL using `DriverManager` with credentials loaded from a local configuration file.
*   **📝 Create**: Insert new student profiles (ID, Name, Course) into the database.
*   **👀 Read**: Fetch and display a formatted list of all registered students.
*   **🔄 Update**: Modify existing student details (e.g., changing a major/course).
*   **❌ Delete**: Remove student records permanently by ID.
*   **🔒 Security**: Implements `.env` file loading to keep sensitive database passwords out of the source code.

---

## 🛠️ Prerequisites

Before running this project, ensure you have the following installed:

*   **Java JDK** (8 or higher)
*   **MySQL Server**
*   **MySQL Connector/J** (JDBC Driver jar file)

---

## ⚙️ Setup & Installation

### 1. Database Setup
Execute the following SQL commands in your MySQL workbench or terminal to prepare the database:

```sql
CREATE DATABASE schooldb;
USE schooldb;

CREATE TABLE students (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    course VARCHAR(100)
);
```

### 2. Configuration
Create a file named `.env` in the root directory of the project (outside `src`). Add your MySQL credentials:

```properties
DB_USER=root
DB_PASSWORD=your_actual_password
```

> **Note:** Ensure `.env` is added to your `.gitignore` if you plan to push this to a public repository.

### 3. Dependencies
Ensure the MySQL Connector JAR is in your classpath.
*   **VS Code**: Add the .jar to "Referenced Libraries".
*   **Command Line**: Include it in the `-cp` flag.

---

## 🏃‍♂️ How to Run

Navigate to the `src` directory and compile the application:

```bash
# Compile
javac App.java

# Run (ensure mysql-connector is in classpath)
# Example for Windows:
java -cp ".;path/to/mysql-connector-j-8.x.x.jar" App
```

---

## 📂 Project Structure

```
JDBCPractice/
├── .env                 # Database credentials (not committed)
├── README.md            # Project documentation
└── src/
    └── App.java         # Main application logic
```