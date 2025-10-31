package System;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Service extends Serializable {
    String getName();
    LocationClass getLocation();
    String getType();
    int getAvgRating();
    long getPrice();

    void newReview(Rating rating);

    Iterator<Rating> listReviews();
    TwoWayIterator<Student> listStudentsInService();
}
