import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;

public class UserHandle {
    private final Map<String, User> users;
    private static final int CARD_WIDTH = 40;

    public UserHandle() {
        users = new HashMap<>();
    }

    public boolean createUser(String name, int age, String id) {
        if (users.containsKey(id)) {
            return false;
        }
        users.put(id, new User(name, age, id));
        return true;
    }

    public boolean updateUser(String id, String name, int age) {
        if (!users.containsKey(id)) {
            return false;
        }
        User user = users.get(id);
        user.setName(name);
        user.setAge(age);
        return true;
    }

    public boolean deleteUser(String id) {
        return users.remove(id) != null;
    }

    public boolean userExists(String id) {
        return users.containsKey(id);
    }

    public User getUser(String id) {
        return users.get(id);
    }

    public void displayUserCard(String id) {
        User user = users.get(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        String name = user.getName();
        int age = user.getAge();

//        ZWA9 RAH GHA DIAL CHAT GPT 3TITLIH LCODE GTLIH ZW9LYA

        String horizontalLine = "+" + "-".repeat(CARD_WIDTH - 2) + "+";
        String emptyLine = "|" + " ".repeat(CARD_WIDTH - 2) + "|";

        System.out.println(horizontalLine);
        System.out.println(emptyLine);
        System.out.printf("| %-" + (CARD_WIDTH - 4) + "s |\n", "User Information");
        System.out.println(emptyLine);
        System.out.printf("| Name: %-" + (CARD_WIDTH - 9) + "s |\n", name);
        System.out.printf("| Age:  %-" + (CARD_WIDTH - 9) + "d |\n", age);
        System.out.printf("| ID:   %-" + (CARD_WIDTH - 9) + "s |\n", id);
        System.out.println(emptyLine);
        System.out.println(horizontalLine);
    }

    public void addConsumption(String id, LocalDate startDate, LocalDate endDate, float carbon) {
        User user = users.get(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        Consumption newConsumption = new Consumption(startDate, endDate, carbon, user);
        user.addConsumption(newConsumption);
        System.out.println("Consumption added successfully.");
    }

    public void displayUserConsumption(String id) {
        User user = users.get(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Consumption> consumptions = user.getConsumptions();
        if (consumptions.isEmpty()) {
            System.out.println("No consumptions found for this user.");
        } else {
            System.out.println("Consumptions for user " + user.getName() + ":");
            for (Consumption consumption : consumptions) {
                System.out.println("Carbon: " + consumption.getCarbon() + " kg, From: " + consumption.getStartDate() + ", To: " + consumption.getEndDate());
            }
        }
    }

    public void displayStats() {
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }

        int totalUsers = users.size();
        int totalConsumptions = 0;
        float totalCarbon = 0;
        User highestConsumer = null;
        float highestConsumption = 0;

        for (User user : users.values()) {
            List<Consumption> userConsumptions = user.getConsumptions();
            totalConsumptions += userConsumptions.size();
            float userTotalCarbon = 0;
            for (Consumption consumption : userConsumptions) {
                userTotalCarbon += consumption.getCarbon();
            }
            totalCarbon += userTotalCarbon;
            if (userTotalCarbon > highestConsumption) {
                highestConsumption = userTotalCarbon;
                highestConsumer = user;
            }
        }

        System.out.println("===== Carbon Consumption Statistics =====");
        System.out.println("Total number of users: " + totalUsers);
        System.out.println("Total number of consumptions: " + totalConsumptions);
        System.out.println("Total carbon consumption: " + totalCarbon + " carbona");
        if (totalConsumptions > 0) {
            System.out.println("Average carbon consumption per entry: " + (totalCarbon / totalConsumptions) + " carbona");
        }
        if (highestConsumer != null) {
            System.out.println("Highest consumer: " + highestConsumer.getName() + " (ID: " + highestConsumer.getId() + ") with " + highestConsumption + " carbona");
        }
        System.out.println("=========================================");
    }
}