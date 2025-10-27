package System;

public class ThriftyStudent extends StudentClass {

    public ThriftyStudent(String id, String name, Service currentLodge) {
        super(id, name, currentLodge);
    }
    private int cheapestPlaceYet = Integer.MAX_VALUE;

    @Override
    public boolean changeLocation(Service service) {
        currentLodge = service;
        if (service instanceof EatingService eatingService) {
            if (eatingService.getPrice() < cheapestPlaceYet) {
                cheapestPlaceYet = eatingService.getPrice();
                return false;
            }
            return true;
        }
        return false;
    }

    public int getCheapestPlaceYet() {
        return cheapestPlaceYet;
    }
}
