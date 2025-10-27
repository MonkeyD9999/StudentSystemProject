package System;

import dataStructures.TwoWayIterator;

import java.io.Serializable;

public abstract class ServiceClass implements Service,Serializable {

    private String name;
    private LocationClass location;
    private String type;

    public ServiceClass(String name,String type, LocationClass location) {
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public LocationClass getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public abstract TwoWayIterator<Student> listStudentsInService();
}
