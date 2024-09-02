import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private int age;
    private final List<Consumption> consumptions;

    public User(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.consumptions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void addConsumption(Consumption consumption) {
        consumptions.add(consumption);
    }

    public List<Consumption> getConsumptions() {
        return consumptions;
    }
}