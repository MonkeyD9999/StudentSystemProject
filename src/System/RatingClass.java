package System;

import java.io.Serializable;

public class RatingClass implements Rating, Serializable {
    private StudentClass student;
    private int stars;
    public RatingClass(StudentClass student, int stars) {
        this.student = student;
        this.stars = stars;
    }

    @Override
    public int getStars() {
        return stars;
    }

    @Override
    public StudentClass getStudent() {
        return student;
    }
}
