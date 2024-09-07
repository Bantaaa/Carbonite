package main;

import java.time.LocalDate;
import java.util.UUID;

public class Consumption {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private float carbon;
    private User user;

    public Consumption(LocalDate startDate, LocalDate endDate, float carbon, User user) {
        this.id = UUID.randomUUID().hashCode();
        this.startDate = startDate;
        this.endDate = endDate;
        this.carbon = carbon;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public float getCarbon() {
        return carbon;
    }

    public void setCarbon(float carbon) {
        this.carbon = carbon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", carbon=" + carbon +
                ", user=" + user.getName() +
                '}';
    }
}