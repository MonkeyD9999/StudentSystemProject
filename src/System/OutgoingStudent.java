package System;


import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.Serializable;

public class OutgoingStudent extends StudentClass implements Serializable {

    public OutgoingStudent(String id, String name, String type, Service lodge) {
        super(id, name, type, lodge);
    }

    private DoublyLinkedList<Service> services = new DoublyLinkedList<Service>();

    @Override
    public boolean changeLocation(Service service) {
        services.addLast(service);
        currentService = service;
        return false;
    }

    @Override
    public void changeLodge(Service service) {
        services.addLast(service);
        currentService = service;
        currentLodge = service;
    }

    @Override
    public boolean hasVisits() {
        return services.isEmpty();
    }

    @Override
    public Iterator<Service> listVisitedServices() {
        return services.iterator();
    }

}
