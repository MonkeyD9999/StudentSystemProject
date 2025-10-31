package System;

import java.io.Serializable;

public class RatingClass implements Rating, Serializable {
    private int stars;
    private String description;
    private String location;

    public RatingClass(int stars, String location, String description) {
        this.stars = stars;
        this.location = location;
        this.description = description;
    }

    @Override
    public int getStars() {
        return stars;
    }

    @Override
    public String getDescription() {
        return description;
    }


}
