package System;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Student extends Serializable {

    public String getName();
    public Service getCurrentService();
    public Service getCurrentLodge();
    public String getCountry();
    public String getType();

    public boolean changeLocation(Service service);
    public void changeLodge(Service service);

    public Iterator<Service> listVisitedServices();

    public void readLodge(Service service);
    public void readCurrent(Service service);

}
