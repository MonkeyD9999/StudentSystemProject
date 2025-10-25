package System;

import dataStructures.DoublyLinkedList;

public class BookishStudent extends StudentClass {
    public BookishStudent(String id, String name) {
        super(id, name);
    }
    private DoublyLinkedList<Service> leisureServices = new DoublyLinkedList<Service>();

    @Override
    public boolean changeLocation(Service service) {
        if (service instanceof LeisureService) {
            leisureServices.addLast(service);
        }
        currentLodge = service;
        return false;
    }
}
