import java.util.HashMap;
import java.util.Map;

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

    public void displayUserCard(String id) {
        User user = users.get(id);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        String name = user.getName();
        int age = user.getAge();

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
}