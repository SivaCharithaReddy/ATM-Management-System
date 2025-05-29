package atm;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ATMManager {
    private Connection conn;

    public ATMManager() throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm_system", "root", "bscr@2004");
    }

    private String hashPin(String pin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashed = md.digest(pin.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashed) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public void createAccount(String userName, String pin) throws Exception {
        String sql = "INSERT INTO accounts (user_name, balance, pin_hash) VALUES (?, 0.0, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, userName);
            stmt.setString(2, hashPin(pin));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newAccNum = generatedKeys.getInt(1);
                System.out.println("Account created successfully. Your account number is: " + newAccNum);
            } else {
                System.out.println("Account created, but account number couldn't be retrieved.");
            }
        }
    }

    public void deposit(int accountNumber, double amount) throws Exception {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountNumber);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    public void withdraw(int accountNumber, double amount) throws Exception {
        String checkSql = "SELECT balance FROM accounts WHERE account_number = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setInt(1, accountNumber);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next() && rs.getDouble("balance") >= amount) {
            String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, accountNumber);
                stmt.executeUpdate();
                System.out.println("Withdrawal successful.");
            }
        } else {
            System.out.println("Insufficient balance or account not found.");
        }
    }

    public double checkBalance(int accountNumber) throws Exception {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");
        }
        System.out.println("Account not found.");
        return -1;
    }

    public void changePin(int accountNumber, String newPin) throws Exception {
        String sql = "UPDATE accounts SET pin_hash = ? WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hashPin(newPin));
            stmt.setInt(2, accountNumber);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("PIN changed successfully.");
            } else {
                System.out.println("Account not found.");
            }
        }
    }

    public boolean validatePin(int accountNumber, String inputPin) throws Exception {
        String sql = "SELECT pin_hash FROM accounts WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("pin_hash").equals(hashPin(inputPin));
            }
        }
        return false;
    }
}
