package System;

import dataStructures.DoublyLinkedList;

import java.io.Serializable;

public interface Student extends Serializable {

    public void moveTo(Service service);

    public void evaluate(Service service);

    public void addVisit(Service service);
    public String getName();
    public Service getCurrentService();
    public Service getCurrentLodge();
    public DoublyLinkedList<Service> getVisited();
    public String getCountry();

    public boolean changeLocation(Service service);
}
