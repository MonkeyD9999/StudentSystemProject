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
        int diff = y.getAvgRating() - x.getAvgRating();
        if (diff != 0) {
            return diff;
        }
       /*
        if (diff > 0) //y maior q x, y primeiro
            return 1;
        if (diff < 0) // x maior q y, x primeiro
            return -1;
        */
        int indexX = rankingOrder.indexOf(x);
        int indexY = rankingOrder.indexOf(y);
        if(indexX == -1 && indexY == -1){
            return 0;
        }
        if(indexX == -1){ // x nao foi avaliado, y primeiro
            return 1;
        }
        if(indexY == -1){ // y nao foi avaliado, x primeiro
            return -1;
        }
        return indexY - indexX;

    }

}
