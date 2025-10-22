package System;

import Exceptions.AlreadyExistsObjectException;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

import java.io.Serial;
import java.io.Serializable;

public class AreaClass implements Area, Serializable {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    private String name;
    private int max_lat;
    private int max_lng;
    private int min_lat;
    private int min_lng;

    private DoublyLinkedList<Service> services;

    public AreaClass(int max_lat, int max_lng, int min_lat, int min_lng) {
        this.max_lat = max_lat;
        this.max_lng = max_lng;
        this.min_lat = min_lat;
        this.min_lng = min_lng;
        services = new DoublyLinkedList<Service>();
    }

    @Override
    public boolean isInside(LocationClass loc) {
        return loc.getLatitude()>=this.min_lat && loc.getLatitude()<=this.max_lat &&
                loc.getLongitude()>=this.min_lng && loc.getLongitude()<=this.max_lng;
    }

    @Override
    public void addService(String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()){
            Service s = it.next();
            if(s.getName().equals(name)){
                throw new AlreadyExistsObjectException();
            }
        }
        services.addLast();
    }




}
