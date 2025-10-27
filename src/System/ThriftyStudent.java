package System;

import dataStructures.Iterator;

public class ThriftyStudent extends StudentClass {

    public ThriftyStudent(String id, String name, String type,Service lodge) {
        super(id, name, type, lodge);
    }


    private int cheapestPlaceYet = Integer.MAX_VALUE;
    private long lodgingRent = Integer.MAX_VALUE;

    @Override
    public boolean changeLocation(Service service) {
        currentService = service;
        if (service instanceof EatingService eatingService) {
            if (eatingService.getPrice() < cheapestPlaceYet) {
                cheapestPlaceYet = eatingService.getPrice();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void changeLodge(Service service) {
        lodgingRent = ((LodgeService) service).getMonthly_cost();
        currentService = service;
        currentLodge = service;
    }

    public boolean cheaperLodge(Service service){
        return ((LodgeService) service).getMonthly_cost() < lodgingRent;
    }

    @Override
    public boolean hasVisits() {
        return false;
    }

    @Override
    public Iterator<Service> listVisitedServices() { return null; }

}
