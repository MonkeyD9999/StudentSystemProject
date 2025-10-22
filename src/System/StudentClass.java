package System;

import dataStructures.DoublyLinkedList;

import java.io.Serializable;


public abstract class StudentClass implements Student, Serializable {
    private String id;
    private String name;
    private Service currentService;
    private DoublyLinkedList<Service> visited;

    public StudentClass(String id, String name) {
        this.id = id;
        this.name = name;
        this.currentService = null;
        this.visited =  new DoublyLinkedList<>();
    }

    public void moveTo(Service service){
        this.currentService = service;
    }

    public void addVisit(Service service){
        this.visited.addLast(service);
    }

    public void evaluate(Service service){
        this.currentService = service;
    }



}
