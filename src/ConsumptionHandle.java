import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsumptionHandle {
    private final List<Consumption> consumptions;
    private final UserHandle userHandle;
    private final Scanner scanner;

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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Enter the value of carbon consumption in carbonat: ");
        float carbonConsumption = scanner.nextFloat();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter the start date (dd/MM/yyyy): ");
        String startDate = scanner.nextLine();
        LocalDate startDateFormatted = LocalDate.parse(startDate, dateTimeFormatter);

        System.out.print("Enter the end date (dd/MM/yyyy): ");
        String endDate = scanner.nextLine();
        LocalDate endDateFormatted = LocalDate.parse(endDate, dateTimeFormatter);

        Consumption consumption = new Consumption(startDateFormatted, endDateFormatted, carbonConsumption, user);
        consumptions.add(consumption);
        user.addConsumption(consumption);

        System.out.println("Consumption added successfully.");
    }

    public void displayUserConsumptions() {
        System.out.print("Enter the ID of the user you want to display consumptions for: ");
        String id = scanner.nextLine();
        User user = userHandle.getUser(id);

        if (user == null) {
            System.out.println("User not found with ID: " + id);
            return;
        }

        List<Consumption> userConsumptions = user.getConsumptions();

        if (userConsumptions.isEmpty()) {
            System.out.println("No consumptions found for user with ID: " + id);
        } else {
            System.out.println("Consumptions for user: " + user.getName());
            for (Consumption consumption : userConsumptions) {
                System.out.println("Carbon consumption: " + consumption.getCarbon() + "carbona" +
                        " from: " + consumption.getStartDate() +
                        " to: " + consumption.getEndDate());
            }
        }
    }

    public void displayAllConsumptions() {
        if (consumptions.isEmpty()) {
            System.out.println("No consumptions recorded in the system.");
        } else {
            System.out.println("All recorded consumptions:");
            for (Consumption consumption : consumptions) {
                System.out.println("User: " + consumption.getUser().getName() +
                        ", Carbon: " + consumption.getCarbon() + "carbona" +
                        ", From: " + consumption.getStartDate() +
                        ", To: " + consumption.getEndDate());
            }
        }
    }

    public void calculateTotalConsumption() {
        float totalConsumption = 0;
        for (Consumption consumption : consumptions) {
            totalConsumption += consumption.getCarbon();
        }
        System.out.println("Total carbon consumption across all users: " + totalConsumption + "carbona");
    }
}