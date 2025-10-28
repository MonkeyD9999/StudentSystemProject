package System;

import dataStructures.Iterator;
import dataStructures.Predicate;

public class ReviewHasTag implements Predicate<Service> {
    private String tag;

    public ReviewHasTag(String tag) {
        this.tag = tag.toLowerCase();
    }

    @Override
    public boolean check(Service service) {
        Iterator<Rating> it = service.listReviews();
        while (it.hasNext()) {
            Rating r = it.next();
            if (r.getDescription().toLowerCase().contains(tag))
                return true;
        }
        return false;
    }
}
