public class LeisureService extends ServiceClass{
    private long ticket_cost;
    private int student_disc;

    public LeisureService(String name, LocationClass location, long ticket_cost, int student_disc) {
        super(name, location);
        this.ticket_cost = ticket_cost;
        this.student_disc = student_disc;

    }
}
