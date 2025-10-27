package System;

import dataStructures.SortedDoublyLinkedList;
import dataStructures.SortedList;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public abstract class ServiceClass implements Service,Serializable {

    private String name;
    private LocationClass location;
    private String type;
    private int reviewCounter;
    private float avgRating;

    public ServiceClass(String name,String type, LocationClass location) {
        this.name = name;
        this.location = location;
        this.type = type;
        avgRating = 0;
        reviewCounter = 0;
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
    public float getAvgRating() { return avgRating; }

    public void newReview(int stars) {
        avgRating = (avgRating * reviewCounter + stars) / ++reviewCounter;
    }

    public abstract TwoWayIterator<Student> listStudentsInService();
}
