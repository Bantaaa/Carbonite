package main.Util;

import main.UserHandle;

import java.util.Scanner;
import java.util.InputMismatchException;

public class UserRelatedUtil {

    public static int getValidChoice(Scanner scanner) {
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

    public static int getValidAge(Scanner scanner) {
        while (true) {
            System.out.print("Enter age: ");
            try {
                int age = scanner.nextInt();
                scanner.nextLine(); // Consume newline
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

    public static String getUniqueUserId(Scanner scanner, UserHandle userHandle) {
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