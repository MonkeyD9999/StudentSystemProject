package System;

import Exceptions.AlreadyExistsObjectException;
import dataStructures.DoublyLinkedList;

import java.io.Serializable;

public interface Area extends Serializable {
    boolean isInside(LocationClass loc);
    void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException;
    String getName();
    DoublyLinkedList<Service> getServices();
    Service getLodge(String name);
}
