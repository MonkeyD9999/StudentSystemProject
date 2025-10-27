package System;

import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Service extends Serializable {
    String getName();
    LocationClass getLocation();
    String getType();
    float getAvgRating();

    void newReview(int stars);

    TwoWayIterator<Student> listStudentsInService();
}
