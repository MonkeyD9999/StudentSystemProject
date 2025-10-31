package System;

import dataStructures.Comparator;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;

public class RatingComparator implements Comparator<Service> {
    private final DoublyLinkedList<Service> rankingOrder;

    public RatingComparator(DoublyLinkedList<Service> rankingOrder) {
        this.rankingOrder = rankingOrder;
    }

    @Override
    public int compare(Service x, Service y) {
        // compare medias
        int diff = y.getAvgRating() - x.getAvgRating();
        if (diff != 0) {
            return diff;
        }

        int indexX = rankingOrder.indexOf(x);
        int indexY = rankingOrder.indexOf(y);


        if(indexX == -1 && indexY == -1){
            return 0;
        }
        if(indexX == -1 || indexY == -1) {
            if(indexX == -1) {
                return -1;
            }
            return 1;
        }
        return indexY - indexX;
    }

}
