package System;

public class EatingService extends ServiceClass {
    private long price;
    private int number_seats;
    public EatingService(String name, LocationClass location, long price, int number_seats) {
        super(name, location);
        this.price = price;
        this.number_seats = number_seats;
    }
}
