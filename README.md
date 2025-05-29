# 🏧 ATM System in Java (MySQL + JDBC)

This is a simple **ATM Management System** built in Java using **JDBC** and **MySQL**. It allows users to create accounts, deposit and withdraw money, check balances, and change their PIN securely.

## 📋 Features

- Create new accounts with 4-digit PINs
- Deposit funds
- Withdraw funds (with PIN verification)
- Check account balance (with PIN verification)
- Change PIN securely
- All PINs are stored as SHA-256 hashed values
## 🛠️ Technologies Used

- Java 11+
- JDBC
- MySQL
- SHA-256 for secure PIN hashing
## 🗂️ Project Structure
atm-system/
│
├── src/
│   └── atm/
│       ├── ATMManager.java     # Core logic for ATM operations
│       └── Main.java           # Menu-based interface for user interaction
│
├── README.md                   # Project overview and instructions


## 🛢️ Database Setup

1. **Create the Database and Table:**

```sql
CREATE DATABASE atm_system;

USE atm_system;

CREATE TABLE accounts (
    account_number INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100),
    balance DOUBLE,
    pin_hash VARCHAR(256)
);

2. **Verify Table:**
USE atm_system;
SELECT * FROM accounts;
## 🚀 How to Run

### 1. **Clone the repository**
git clone https://github.com/your-username/atm-system-java.git
cd atm-system-java

### 2. **Configure Database Connection**

Update the database URL, username, and password in `ATMManager.java`:

```java
conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/atm_system", "root", "your_password"
);
```

### 3. **Compile and Run**

You can use any Java IDE (like IntelliJ or Eclipse) or run via terminal:
javac -d out src/atm/*.java
java -cp out atm.Main
## 🔒 Security Notes

* All PINs are hashed with SHA-256 before being stored in the database.
* PINs are verified by comparing hashes rather than plain values.
* Consider using salted hashing algorithms like **bcrypt** for production-level security.

## 📌 TODO

* Add transaction history table and view
* Use connection pooling (HikariCP or similar)
* Enhance UI with Swing or JavaFX
* Improve error handling and logging

## 📃 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

* **Your Name**
  [GitHub Profile](https://github.com/your-username)
