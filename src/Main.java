import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserHandle userHandle = new UserHandle();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getValidChoice();

            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    modifyUser();
                    break;
                case 3:
//                    showCarbonConsumption();
                    break;
                case 4:
//                    addConsumption();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
//                    showStats();
                    break;
                case 7:
                    displayUserCard();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
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
        System.out.println("      0. Exit");
        System.out.println("===================================================================");
    }

    private static int getValidChoice() {
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static void addNewUser() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        int age = getValidAge();
        String id = getUniqueUserId();
        if (userHandle.createUser(name, age, id)) {
            System.out.println("User added successfully. User ID: " + id);
            userHandle.displayUserCard(id);
        } else {
            System.out.println("Failed to add user. Please try again.");
        }
    }

    private static void modifyUser() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        if (userHandle.userExists(id)) {
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            int age = getValidAge();
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

//    private static void showCarbonConsumption() {
//        scanner.nextLine(); // Consume newline
//        System.out.print("Enter user ID: ");
//        String id = scanner.nextLine();
//        userHandle.displayUserConsumption(id);
//    }

    private static void addConsumption() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        if (userHandle.userExists(id)) {
            try {
                System.out.print("Enter consumption amount (in kg CO2): ");
                double amount = scanner.nextDouble();
                scanner.nextLine();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDateStr = scanner.nextLine();
                LocalDate startDate = LocalDate.parse(startDateStr, formatter);

                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDateStr = scanner.nextLine();
                LocalDate endDate = LocalDate.parse(endDateStr, formatter);
                Consumption.add()
                if (Consumption.Consumption(id, startDate, endDate, amount)) {
                    System.out.println("Consumption added successfully.");
                } else {
                    System.out.println("Failed to add consumption. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for consumption amount. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private static void deleteUser() {
        scanner.nextLine();
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        if (userHandle.deleteUser(id)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found or deletion failed.");
        }
    }

//    private static void showStats() {
//        UserHandle.displayStats();
//    }

    private static void displayUserCard() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter user ID: ");
        String id = scanner.nextLine();
        userHandle.displayUserCard(id);
    }

    private static int getValidAge() {
        while (true) {
            System.out.print("Enter age: ");
            try {
                int age = scanner.nextInt();
                if (age < 0 || age > 150) {
                    System.out.println("Invalid age. Please enter a number between 0 and 150.");
                } else {
                    return age;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear input
            }
        }
    }

    private static String getUniqueUserId() {
        scanner.nextLine(); // Consume newline
        String id;
        do {
            System.out.print("Enter user ID: ");
            id = scanner.nextLine();
            if (userHandle.userExists(id)) {
                System.out.println("This ID already exists. Please choose a different one.");
            }
        } while (userHandle.userExists(id));
        return id;
    }
}