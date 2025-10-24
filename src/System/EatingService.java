package System;

public class EatingService extends ServiceClass {
    private int price;
    private int number_seats;
    public EatingService(String name, String type, LocationClass location, int price, int number_seats) {
        super(name, type, location);
        this.price = price;
        this.number_seats = number_seats;
    }
}
