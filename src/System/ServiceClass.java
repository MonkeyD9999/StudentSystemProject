package System;

import dataStructures.*;

import java.io.Serializable;

public abstract class ServiceClass implements Service,Serializable {

    private String name;
    private LocationClass location;
    private String type;
    private int reviewCounter;
    private float avgRating;
    private DoublyLinkedList<Rating> reviews;

    public ServiceClass(String name,String type, LocationClass location) {
        this.name = name;
        this.location = location;
        this.type = type;
        avgRating = 4;
        reviewCounter = 0;
        reviews = new DoublyLinkedList<Rating>();
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
    public int getAvgRating() { return Math.round(avgRating); }

    public void newReview(int stars) {
        avgRating = (avgRating * reviewCounter + stars) / ++reviewCounter;
    }

    public Iterator<Rating> listReviews() { return reviews.iterator(); }

    public abstract TwoWayIterator<Student> listStudentsInService();
}
