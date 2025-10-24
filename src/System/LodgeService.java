package System;

public class LodgeService extends ServiceClass {
    private long monthly_cost;
    private int number_rooms;
    private int used_rooms;

    public LodgeService(String name, String type, LocationClass location, int monthly_cost, int number_rooms) {
        super(name,type, location);
        this.monthly_cost = monthly_cost;
        this.number_rooms = number_rooms;
        this.used_rooms = 0;
    }

    public boolean isFull() {
        return number_rooms == used_rooms;
    }
}
