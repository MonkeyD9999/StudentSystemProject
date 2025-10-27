package System;

import dataStructures.Comparator;

public class RatingComparator implements Comparator<Service> {

    @Override
    public int compare(Service x, Service y) {
        double diff = y.getAvgRating() - x.getAvgRating();
        if (diff > 0)
            return 1;
        else if (diff < 0)
            return -1;
        else
            return 0;
    }

}
