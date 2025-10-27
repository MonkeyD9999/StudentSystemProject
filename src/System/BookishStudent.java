package System;

import dataStructures.DoublyLinkedList;

public class BookishStudent extends StudentClass {
    public BookishStudent(String id, String name, String type) {
        super(id, name, type);
    }
    private DoublyLinkedList<Service> leisureServices = new DoublyLinkedList<Service>();

    @Override
    public boolean changeLocation(Service service) {
        if (service instanceof LeisureService) {
            leisureServices.addLast(service);
        }
        currentService = service;
        return false;
    }

    @Override
    public void changeLodge(Service service) {
        currentService = service;
        currentLodge = service;
    }


}
