package main;

import java.time.LocalDate;
import java.util.*;
import java.time.temporal.ChronoUnit;
import static main.Util.DateRelatedUtil.*;

public class ConsumptionHandle {
    private final List<Consumption> consumptions;
    private final UserHandle userHandle;

    public ConsumptionHandle(UserHandle userHandle) {
        this.consumptions = new ArrayList<>();
        this.userHandle = userHandle;
    }

    public void manageConsumption(Scanner scanner) {
        System.out.print("Enter the ID of the user you want to add a consumption to: ");
        String id = scanner.nextLine();
        User user = userHandle.getUser(id);

        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        System.out.print("Enter the value of carbon consumption in carbona: ");
        float carbonConsumption = scanner.nextFloat();
        scanner.nextLine(); // Consume newline

        LocalDate startDate = getDateInput(scanner, "Enter the start date (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput(scanner, "Enter the end date (dd/MM/yyyy): ");

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date. Please try again.");
            return;
        }

        Consumption consumption = new Consumption(startDate, endDate, carbonConsumption, user);
        consumptions.add(consumption);
        user.addConsumption(consumption);

        System.out.println("Consumption added successfully.");
    }

    public void generateReport(Scanner scanner) {
        System.out.print("Enter the ID of the user you want to generate a report for: ");
        String id = scanner.nextLine();
        User user = userHandle.getUser(id);

        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        LocalDate startDate = getDateInput(scanner, "Enter the start date of the period (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput(scanner, "Enter the end date of the period (dd/MM/yyyy): ");

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date. Please try again.");
            return;
        }

        List<Consumption> userConsumptions = user.getConsumptions();
        double totalConsumption = calculateTotalConsumption(userConsumptions, startDate, endDate);

        System.out.println("\nReport for user: " + user.getName());
        System.out.println("Period: " + formatDate(startDate) + " to " + formatDate(endDate));
        System.out.printf("Total consumption for the period: %.2f carbona\n", totalConsumption);

        double dailyConsumption = calculateDailyConsumption(userConsumptions, startDate);
        if (dailyConsumption > 0) {
            System.out.printf("Daily consumption (for %s): %.2f carbona\n", formatDate(startDate), dailyConsumption);
        } else {
            System.out.printf("No consumption data available for %s\n", formatDate(startDate));
        }

        double weeklyConsumption = calculateWeeklyConsumption(userConsumptions, startDate, endDate);
        System.out.printf("Weekly consumption: %.2f carbona\n", weeklyConsumption);

        double periodConsumption = calculatePeriodConsumption(userConsumptions, startDate, endDate);
        System.out.printf("Total consumption for the specified period: %.2f carbona\n", periodConsumption);

        int daysWithConsumption = 0;
        double totalConsumptionForAverage = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            double dailyConsumptionForAverage = calculateDailyConsumption(userConsumptions, currentDate);
            if (dailyConsumptionForAverage > 0) {
                totalConsumptionForAverage += dailyConsumptionForAverage;
                daysWithConsumption++;
            }
            currentDate = currentDate.plusDays(1);
        }

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if (daysWithConsumption > 0) {
            double averageDailyConsumption = totalConsumptionForAverage / daysWithConsumption;
            System.out.printf("Average daily consumption (for days with data): %.2f carbona\n", averageDailyConsumption);
            System.out.printf("Days with consumption data: %d out of %d\n", daysWithConsumption, totalDays);
        } else {
            System.out.println("No consumption data available for the specified period.");
        }
    }

    private double calculateDailyConsumption(List<Consumption> consumptions, LocalDate date) {
        double dailyTotal = 0;
        for (Consumption consumption : consumptions) {
            if (!consumption.getStartDate().isAfter(date) && !consumption.getEndDate().isBefore(date)) {
                long daysInPeriod = ChronoUnit.DAYS.between(consumption.getStartDate(), consumption.getEndDate()) + 1;
                dailyTotal += consumption.getCarbon() / daysInPeriod;
            }
        }
        return dailyTotal;
    }

    private double calculateWeeklyConsumption(List<Consumption> consumptions, LocalDate startDate, LocalDate endDate) {
        LocalDate weekEnd = startDate.plusDays(6).isAfter(endDate) ? endDate : startDate.plusDays(6);
        double weeklyTotal = calculatePeriodConsumption(consumptions, startDate, weekEnd);
        long daysInPeriod = ChronoUnit.DAYS.between(startDate, weekEnd) + 1;
        if (daysInPeriod < 7) {
            weeklyTotal = (weeklyTotal / daysInPeriod) * 7;
        }
        return weeklyTotal;
    }

    private double calculatePeriodConsumption(List<Consumption> consumptions, LocalDate startDate, LocalDate endDate) {
        double periodTotal = 0;
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            boolean dayIncluded = false;
            for (Consumption consumption : consumptions) {
                if (!consumption.getStartDate().isAfter(currentDate) && !consumption.getEndDate().isBefore(currentDate)) {
                    dayIncluded = true;
                    break;
                }
            }
            if (dayIncluded) {
                periodTotal += calculateDailyConsumption(consumptions, currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }
        return periodTotal;
    }

    private double calculateTotalConsumption(List<Consumption> consumptions, LocalDate startDate, LocalDate endDate) {
        double totalConsumption = 0;
        for (Consumption consumption : consumptions) {
            if (consumption.getStartDate().isAfter(endDate) || consumption.getEndDate().isBefore(startDate)) {
                continue; // Skip
            }

            LocalDate overlapStart = consumption.getStartDate().isAfter(startDate) ? consumption.getStartDate() : startDate;
            LocalDate overlapEnd = consumption.getEndDate().isBefore(endDate) ? consumption.getEndDate() : endDate;

            long overlapDays = ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1;
            long totalConsumptionDays = ChronoUnit.DAYS.between(consumption.getStartDate(), consumption.getEndDate()) + 1;

            double consumptionInPeriod = (consumption.getCarbon() * overlapDays) / totalConsumptionDays;
            totalConsumption += consumptionInPeriod;
        }
        return totalConsumption;
    }
}