package System;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;
import java.security.PublicKey;

public interface Student extends Serializable {

    /**
     *
     * @return name
     */
    public String getName();

    /**
     *
     * @return current service
     */
    public Service getCurrentService();

    /**
     *
     * @return current lodge service
     */
    public Service getCurrentLodge();

    /**
     *
     * @return the student country
     */
    public String getCountry();

    /**
     *
     * @param service Service to move
     * @return true if thrifty is distracted
     */
    public boolean changeLocation(Service service);

    /**
     *
     * @param service Service to be the new lodge
     */
    public void changeLodge(Service service);

    /**
     *
     * @return Iterator with all the visited places
     */
    public Iterator<Service> listVisitedServices();

    /**
     *
     * @return Type of the student
     */
    public String getType();

}
