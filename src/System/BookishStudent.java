package System;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.Serializable;

public class BookishStudent extends StudentClass implements Serializable {
    public BookishStudent(String id, String name, String type, Service lodge) {
        super(id, name, type, lodge);
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

    @Override
    public Iterator<Service> listVisitedServices() {
        return leisureServices.iterator();
    }

    @Override
    public boolean hasVisits() {
        return leisureServices.isEmpty();
    }

}
