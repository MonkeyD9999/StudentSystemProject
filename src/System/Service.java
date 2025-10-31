package System;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Service extends Serializable {
    /**
     *
     * @return name
     */
    String getName();

    /**
     *
     * @return Location
     */
    LocationClass getLocation();

    /**
     *
     * @return type of the service
     */
    String getType();

    /**
     *
     * @return Average rating
     */
    int getAvgRating();

    /**
     *
     * @return Price
     */
    double getPrice();

    /**
     *
     * @param rating New rating
     */
    void newReview(Rating rating);

    /**
     *
     * @return Iterator with all the ratings
     */
    Iterator<Rating> listReviews();

    /**
     *
     * @return Iterator with all the students in the service
     */
    TwoWayIterator<Student> listStudentsInService();
}
