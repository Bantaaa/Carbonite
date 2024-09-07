package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import static main.Util.UserRelatedUtil.*;
import static main.Util.DateRelatedUtil.getDateInput;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserHandle userHandle = new UserHandle();
    private static final ConsumptionHandle consumptionHandle = new ConsumptionHandle(userHandle);

    public static void main(String[] args) {
        if (!connectToDatabase()) {
            return;
        }

        while (true) {
            displayMenu();
            int choice = getValidChoice(scanner);

            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    modifyUser();
                    break;
                case 3:
                    System.out.println("Feature not implemented yet.");
                    break;
                case 4:
                    addConsumption();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    showStats();
                    break;
                case 7:
                    displayUserCard();
                    break;
                case 8:
                    generateReport();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static boolean connectToDatabase() {
        String url = "jdbc:postgresql://localhost:5432/green_pulse";
        String user = "GreenPulse";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
                return true;
            } else {
                System.out.println("Failed to make connection!");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return false;
        }
    }

    private static void displayMenu() {
        System.out.println("\n    __   ____  ____   ____    ___   ____   ____  ______    ___ ");
        System.out.println("   /  ] /    ||    \\ |    \\  /   \\ |    \\ |    ||      |  /  _]");
        System.out.println("  /  / |  o  ||  D  )|  o  )|     ||  _  | |  | |      | /  [_ ");
        System.out.println(" /  /  |     ||    / |     ||  O  ||  |  | |  | |_|  |_||    _]");
        System.out.println("/   \\_ |  _  ||    \\ |  O  ||     ||  |  | |  |   |  |  |   [_ ");
        System.out.println("\\     ||  |  ||  .  \\|     ||     ||  |  | |  |   |  |  |     |");
        System.out.println(" \\____||__|__||__|\\_||_____| \\___/ |__|__||____|  |__|  |_____|");
        System.out.println("\n===================================================================");
        System.out.println("                            M E N U ");
        System.out.println("===================================================================");
        System.out.println("      1. Add a new user");
        System.out.println("      2. Modify user");
        System.out.println("      3. Show my whole carbon consumption");
        System.out.println("      4. Add a consumption");
        System.out.println("      5. Delete a user");
        System.out.println("      6. Show stats");
        System.out.println("      7. Display user card");
        System.out.println("      8. Generate consumption report");
        System.out.println("      0. Exit");
        System.out.println("===================================================================");
    }

    private static void addNewUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        int age = getValidAge(scanner);
        String id = getUniqueUserId(scanner, userHandle);
        if (userHandle.createUser(name, age, id)) {
            System.out.println("User added successfully. User ID: " + id);
            userHandle.displayUserCard(id);
        } else {
            System.out.println("Failed to add user. Please try again.");
        }
    }

    private static void modifyUser() {
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        if (userHandle.userExists(id)) {
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            int age = getValidAge(scanner);
            if (userHandle.updateUser(id, name, age)) {
                System.out.println("User updated successfully.");
                userHandle.displayUserCard(id);
            } else {
                System.out.println("Failed to update user. Please try again.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void addConsumption() {
        consumptionHandle.manageConsumption(scanner);
    }

    private static void deleteUser() {
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        if (userHandle.deleteUser(id)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found or deletion failed.");
        }
    }

    private static void showStats() {
        userHandle.displayStats();
    }

    private static void displayUserCard() {
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        userHandle.displayUserCard(id);
    }

    private static void generateReport() {
        consumptionHandle.generateReport(scanner);
    }
}