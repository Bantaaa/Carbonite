package main.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateRelatedUtil {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate getDateInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            }
        }
    }

    public static String formatDate(LocalDate date) {
        return date.format(dateFormatter);
    }
}