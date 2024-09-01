import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private int age;
    private static final List<User> users = new ArrayList<>();

    public User(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public User() {
    }

    public static void createUser(String name, int age, String id) {
        users.add(new User(name, age, id));
    }

    public static User getUser(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void addUser(User user) {
        users.add(user);
    }
}