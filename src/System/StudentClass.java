package System;

import dataStructures.Iterator;

import java.io.Serializable;


public abstract class StudentClass implements Student, Serializable {
    private String country;
    private String name;
    private String type;
    protected Service currentLodge;
    protected Service currentService;

    public StudentClass(String name, String country, String type, Service lodge) {
        this.country = country;
        this.name = name;
        this.type = type;
        this.currentLodge = lodge;
        this.currentService = lodge;
    }

    public void moveTo(Service service){
        this.currentLodge = service;
    }

    public void evaluate(Service service){
        this.currentLodge = service;
    }

    public String getName() {
        return name;
    }
    public String getType() { return type; }
    public Service getCurrentService() { return currentService; }
    public Service getCurrentLodge() {
        return currentLodge;
    }
    public String getCountry() {
        return country;
    }

    public abstract boolean hasVisits();

    public abstract Iterator<Service> listVisitedServices();

    public abstract boolean changeLocation(Service service);
    public abstract void changeLodge(Service service);

}
