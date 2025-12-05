package System;

import dataStructures.Iterator;
import dataStructures.Map;
import dataStructures.Predicate;

public class ReviewHasTag implements Predicate<Service> {
    private String tag;

    public ReviewHasTag(String tag) {
        this.tag = tag.toLowerCase();
    }

    @Override
    public boolean check(Service service) {
        Iterator<Map.Entry<Integer, Rating>> it = service.listReviews();
        while (it.hasNext()) {
            Rating r = it.next().value();
            String [] parts = r.getDescription().split(" ");
            for(int i = 0; i < parts.length; i++) {
                if(parts[i].toLowerCase().equalsIgnoreCase(tag)) {
                    return true;
                }
            }
        }
        return false;
    }
    /*
    System.out.println(r.getDescription());
    System.out.println(tag);
     */
}
