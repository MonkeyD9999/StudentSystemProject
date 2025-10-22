package System;

import java.io.Serializable;

public abstract class ServiceClass implements Serializable {

    private String name;
    private LocationClass location;

    public ServiceClass(String name, LocationClass location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }
    public LocationClass getLocation() {
        return location;
    }
}
