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

    private final String EAT="eating";
    private final String LODGE="lodging";
    private final String LEISURE="leisure";

    private String name;
    private long latMax;
    private long lngMax;
    private long latMin;
    private long lngMin;

    private DoublyLinkedList<Service> services;

    public AreaClass(String name, long latMax, long lngMax, long latMin, long lngMin) {
        this.name = name;
        this.latMax = latMax;
        this.lngMax = lngMax;
        this.latMin = latMin;
        this.lngMin = lngMin;
        services = new DoublyLinkedList<Service>();
    }

    @Override
    public boolean isInside(LocationClass loc) {
        return loc.getLatitude()>=this.latMin && loc.getLatitude()<=this.latMax &&
                loc.getLongitude()>=this.lngMin && loc.getLongitude()<=this.lngMax;
    }

    @Override
    public void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()){
            Service s = it.next();
            if(s.getName().equals(name)){
                throw new AlreadyExistsObjectException(name);
            }
        }
        switch (type){
            case EAT -> services.addLast(new EatingService(name, type, loc, price, value));
            case LODGE -> services.addLast(new LodgeService(name, type, loc, price, value));
            case LEISURE -> services.addLast(new LeisureService(name, type, loc, price, value));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DoublyLinkedList<Service> getServices() {
        return services;
    }

    @Override
    public Service getLodge(String name) {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()){
            Service s = it.next();
            if(s.getName().equals(name)){
                return s;
            }
        }
        return null;
    }


}
