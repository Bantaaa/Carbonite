import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.time.temporal.ChronoUnit;

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

        System.out.print("Enter the value of carbon consumption in carbona: ");
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

        List<Consumption> userConsumptions = user.getConsumptions();
        if (userConsumptions.isEmpty()) {
            System.out.println("No consumptions found for user with ID: " + id);
            return;
        }

        Map<LocalDate, Double> dailyConsumption = new TreeMap<>();
        LocalDate earliestDate = LocalDate.MAX;
        LocalDate latestDate = LocalDate.MIN;

        for (Consumption consumption : userConsumptions) {
            LocalDate startDate = consumption.getStartDate();
            LocalDate endDate = consumption.getEndDate();
            long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            double dailyValue = consumption.getCarbon() / days;

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                dailyConsumption.merge(date, dailyValue, Double::sum);
            }

            if (startDate.isBefore(earliestDate)) earliestDate = startDate;
            if (endDate.isAfter(latestDate)) latestDate = endDate;
        }

        System.out.println("\nReport for user: " + user.getName());
        System.out.println("Period: " + earliestDate + " to " + latestDate);

        // Daily report
        System.out.println("\nDaily Consumption:");
        for (Map.Entry<LocalDate, Double> entry : dailyConsumption.entrySet()) {
            System.out.printf("%s: %.2f carbona\n", entry.getKey(), entry.getValue());
        }

        // Weekly report
        System.out.println("\nWeekly Consumption:");
        Map<String, Double> weeklyConsumption = new TreeMap<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        for (Map.Entry<LocalDate, Double> entry : dailyConsumption.entrySet()) {
            LocalDate date = entry.getKey();
            String weekKey = date.getYear() + "-W" + date.get(weekFields.weekOfWeekBasedYear());
            weeklyConsumption.merge(weekKey, entry.getValue(), Double::sum);
        }
        for (Map.Entry<String, Double> entry : weeklyConsumption.entrySet()) {
            System.out.printf("%s: %.2f carbona\n", entry.getKey(), entry.getValue());
        }

        // Monthly report
        System.out.println("\nMonthly Consumption:");
        Map<String, Double> monthlyConsumption = new TreeMap<>();
        for (Map.Entry<LocalDate, Double> entry : dailyConsumption.entrySet()) {
            String monthYear = entry.getKey().getYear() + "-" + String.format("%02d", entry.getKey().getMonthValue());
            monthlyConsumption.merge(monthYear, entry.getValue(), Double::sum);
        }
        for (Map.Entry<String, Double> entry : monthlyConsumption.entrySet()) {
            System.out.printf("%s: %.2f carbona\n", entry.getKey(), entry.getValue());
        }
    }
}