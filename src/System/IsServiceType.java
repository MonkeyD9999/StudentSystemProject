package System;

import dataStructures.Predicate;

public class IsServiceType implements Predicate<Service> {

    private String type;

    public IsServiceType(String type){
        this.type = type;
    }

    public boolean check(Service service) {
        return service.getType().equalsIgnoreCase(type);
    }
}
