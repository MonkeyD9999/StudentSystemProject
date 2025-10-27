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
    private long topLeftLat;
    private long topLeftLong;
    private long bottomRightLat;
    private long bottomRightLong;

    private DoublyLinkedList<Service> services;

    public AreaClass(String name, long topLeftLat, long topLeftLong, long bottomRightLat, long bottomRightLong) {
        this.name = name;
        this.topLeftLat = topLeftLat;
        this.topLeftLong = topLeftLong;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLong = bottomRightLong;
        services = new DoublyLinkedList<Service>();
    }

    /*
    permite ser igual/tar no limite (checar se occorre erro)
     */
    @Override
    public boolean isInside(LocationClass loc) {
        return loc.getLatitude()<=topLeftLat && loc.getLatitude()>=bottomRightLat &&
                loc.getLongitude()>=topLeftLong && loc.getLongitude()<=bottomRightLong;
    }

    @Override
    public void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()){
            Service s = it.next();
            if(s.getName().equalsIgnoreCase(name)){
                throw new AlreadyExistsObjectException(s.getName());
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
