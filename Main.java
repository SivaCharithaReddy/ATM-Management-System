package atm;

import java.util.Scanner;

public class Main {

    private static int getIntInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static String getValidPin(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String pin = scanner.next();
            if (pin.matches("\\d{4}")) {
                return pin;
            } else {
                System.out.println("Invalid PIN. Please enter a 4-digit number.");
            }
        }
    }

    public static void main(String[] args) {
        try {
            ATMManager atm = new ATMManager();
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- ATM System Menu ---");
                System.out.println("1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Change PIN");
                System.out.println("6. Exit");

                choice = getIntInput(scanner, "Enter choice: ");

                switch (choice) {
                    case 1:
                        System.out.print("Enter user name: ");
                        String name = scanner.next();
                        String pin = getValidPin(scanner, "Set 4-digit PIN: ");
                        atm.createAccount(name, pin);
                        break;
                    case 2:
                        int accNumD = getIntInput(scanner, "Account number: ");
                        System.out.print("Amount: ");
                        double amtD = scanner.nextDouble();
                        atm.deposit(accNumD, amtD);
                        break;
                    case 3:
                        int accNumW = getIntInput(scanner, "Account number: ");
                        String pinW = getValidPin(scanner, "PIN: ");
                        if (atm.validatePin(accNumW, pinW)) {
                            System.out.print("Amount: ");
                            double amtW = scanner.nextDouble();
                            atm.withdraw(accNumW, amtW);
                        } else {
                            System.out.println("Invalid PIN.");
                        }
                        break;
                    case 4:
                        int accNumB = getIntInput(scanner, "Account number: ");
                        String pinB = getValidPin(scanner, "PIN: ");
                        if (atm.validatePin(accNumB, pinB)) {
                            System.out.println("Balance: " + atm.checkBalance(accNumB));
                        } else {
                            System.out.println("Invalid PIN.");
                        }
                        break;
                    case 5:
                        int accNumP = getIntInput(scanner, "Account number: ");
                        String oldPin = getValidPin(scanner, "Old PIN: ");
                        if (atm.validatePin(accNumP, oldPin)) {
                            String newPin = getValidPin(scanner, "New PIN: ");
                            atm.changePin(accNumP, newPin);
                        } else {
                            System.out.println("Invalid old PIN.");
                        }
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 6);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
