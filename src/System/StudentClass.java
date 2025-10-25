package System;

import dataStructures.DoublyLinkedList;

import java.io.Serializable;


public abstract class StudentClass implements Student, Serializable {
    private String country;
    private String name;
    protected Service currentLodge;
    protected Service currentService;
    private DoublyLinkedList<Service> visited;

    public StudentClass(String name, String country) {
        this.country = country;
        this.name = name;
        this.currentLodge = null;
        this.currentService = null;
        this.visited =  new DoublyLinkedList<>();
    }

    public void moveTo(Service service){
        this.currentLodge = service;
    }

    public void addVisit(Service service){
        this.visited.addLast(service);
    }

    public void evaluate(Service service){
        this.currentLodge = service;
    }

    public String getName() {
        return name;
    }
    public Service getCurrentService() { return currentService; }
    public Service getCurrentLodge() {
        return currentLodge;
    }
    public DoublyLinkedList<Service> getVisited() {
        return visited;
    }
    public String getCountry() {
        return country;
    }

    public abstract boolean changeLocation(Service service);

}
