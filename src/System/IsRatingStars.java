package System;

import dataStructures.Predicate;

public class IsRatingStars implements Predicate<Service> {

    private int stars;

    public IsRatingStars(int stars){ this.stars = stars; }

    public boolean check(Service service){
        return service.getAvgRating() == stars;
    }
}
