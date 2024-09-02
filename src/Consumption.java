import java.time.LocalDate;

public class Consumption {
    private int idUser;
    private double consumption;
    private LocalDate startDate;
    private LocalDate endDate;



    public Consumption(int id , LocalDate startDate, LocalDate endDate, double consumption)
    {
        this.idUser = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.consumption = consumption;
    }

}
