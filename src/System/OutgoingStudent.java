package System;


import dataStructures.DoublyLinkedList;

public class OutgoingStudent extends StudentClass {

    public OutgoingStudent(String id, String name) {
        super(id, name);
    }
    private DoublyLinkedList<Service> services = new DoublyLinkedList<Service>();

    @Override
    public boolean changeLocation(Service service) {
        services.addLast(service);
        currentLodge = service;
        return false;
    }
}
