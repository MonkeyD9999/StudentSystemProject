package System;

public class EatingService extends ServiceClass {
    private int price;
    private int number_seats;
    private int count_seats;
    public EatingService(String name, String type, LocationClass location, int price, int number_seats) {
        super(name, type, location);
        this.price = price;
        this.number_seats = number_seats;
        this.count_seats = 0;
    }

    public int getPrice() { return price; }

    public int getNumber_seats() { return number_seats; }

    public boolean isFull(){ return count_seats >= number_seats; }
}
