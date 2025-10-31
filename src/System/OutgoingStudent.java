package System;


import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.Serializable;

public class OutgoingStudent extends StudentClass implements Serializable {

    public OutgoingStudent(String id, String name, String type, Service lodge) {
        super(id, name, type, lodge);
        services.addFirst(lodge);
    }

    private DoublyLinkedList<Service> services = new DoublyLinkedList<Service>();

    @Override
    public boolean changeLocation(Service service) {
        if (!hasVisited(service.getName()))
            services.addLast(service);

        currentService = service;
        return false;
    }

    @Override
    public void changeLodge(Service service) {
        if (!hasVisited(service.getName()))
            services.addLast(service);

        currentService = service;
        currentLodge = service;
    }

    @Override
    public Iterator<Service> listVisitedServices() {
        return services.iterator();
    }

    private boolean hasVisited(String location) {
        Iterator<Service> iterator = services.iterator();
        while (iterator.hasNext()) {
            Service service = iterator.next();
            if (service.getName().equalsIgnoreCase(location))
                return true;
        }
        return false;
    }
}
