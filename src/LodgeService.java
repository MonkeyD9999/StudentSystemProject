public class LodgeService extends ServiceClass{
    private long monthly_cost;
    private int number_rooms;

    public LodgeService(String name, LocationClass location, long monthly_cost, int number_rooms) {
        super(name, location);
        this.monthly_cost = monthly_cost;
        this.number_rooms = number_rooms;
    }
}
