package System;

import dataStructures.Iterator;

import java.io.Serializable;

public class ThriftyStudent extends StudentClass implements Serializable {


    public ThriftyStudent(String id, String name, String type,Service lodge) {
        super(id, name, type, lodge);
        if (lodge != null)
            lodgingRent = (long) lodge.getPrice();
        else
            lodgingRent = Long.MAX_VALUE;
    }


    private double cheapestPlaceYet = Long.MAX_VALUE;
    private long lodgingRent;

    @Override
    public boolean changeLocation(Service service) {
        currentService = service;
        if (service instanceof EatingService eatingService) {
            if (eatingService.getPrice() <= cheapestPlaceYet) {
                cheapestPlaceYet =  eatingService.getPrice();
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void changeLodge(Service service) {
        lodgingRent = (long) ((LodgeService) service).getPrice();
        currentService = service;
        currentLodge = service;
    }

    public boolean cheaperLodge(Service service){
        return service.getPrice() < lodgingRent;
    }


}
