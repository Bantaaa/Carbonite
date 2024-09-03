import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.time.temporal.ChronoUnit;

public class ConsumptionHandle {
    private final List<Consumption> consumptions;
    private final UserHandle userHandle;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsumptionHandle(UserHandle userHandle) {
        this.consumptions = new ArrayList<>();
        this.userHandle = userHandle;
        this.scanner = new Scanner(System.in);
    }

    public void manageConsumption() {
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

        LocalDate startDate = getDateInput("Enter the start date (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput("Enter the end date (dd/MM/yyyy): ");

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date. Please try again.");
            return;
        }

        Consumption consumption = new Consumption(startDate, endDate, carbonConsumption, user);
        consumptions.add(consumption);
        user.addConsumption(consumption);

        System.out.println("Consumption added successfully.");
    }

    public void displayUserConsumptions() {
    }

    public void displayAllConsumptions() {
    }

    public void calculateTotalConsumption() {
        float totalConsumption = 0;
        for (Consumption consumption : consumptions) {
            totalConsumption += consumption.getCarbon();
        }
        System.out.println("Total carbon consumption across all users: " + totalConsumption + " carbona");
    }

    public void generateReport() {
        System.out.print("Enter the ID of the user you want to generate a report for: ");
        String id = scanner.nextLine();
        User user = userHandle.getUser(id);

        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        LocalDate startDate = getDateInput("Enter the start date of the period (dd/MM/yyyy): ");
        LocalDate endDate = getDateInput("Enter the end date of the period (dd/MM/yyyy): ");

        if (startDate.isAfter(endDate)) {
            System.out.println("Start date cannot be after end date. Please try again.");
            return;
        }

        List<Consumption> userConsumptions = user.getConsumptions();
        double totalConsumption = calculateTotalConsumption(userConsumptions, startDate, endDate);

        System.out.println("\nReport for user: " + user.getName());
        System.out.println("Period: " + startDate.format(dateFormatter) + " to " + endDate.format(dateFormatter));
        System.out.printf("Total consumption for the period: %.2f carbona\n", totalConsumption);

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double dailyAverage = totalConsumption / totalDays;
        System.out.printf("Daily average consumption: %.2f carbona\n", dailyAverage);

        double weeklyAverage = dailyAverage * 7;
        System.out.printf("Weekly average consumption: %.2f carbona\n", weeklyAverage);

        double monthlyAverage = dailyAverage * 30; // Assuming a 30-day month for simplicity
        System.out.printf("Monthly average consumption: %.2f carbona\n", monthlyAverage);
    }

    private double calculateTotalConsumption(List<Consumption> consumptions, LocalDate startDate, LocalDate endDate) {
        double totalConsumption = 0;
        for (Consumption consumption : consumptions) {
            if (consumption.getStartDate().isAfter(endDate) || consumption.getEndDate().isBefore(startDate)) {
                continue; // Skip consumptions outside the requested period
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

    private LocalDate getDateInput(String prompt) {
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
}
