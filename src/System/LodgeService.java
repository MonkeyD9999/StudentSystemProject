package System;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

public class LodgeService extends ServiceClass {
    private long monthly_cost;
    private int number_rooms;
    private DoublyLinkedList<Student> costumers;

    public LodgeService(String name, String type, LocationClass location, int monthly_cost, int number_rooms) {
        super(name,type, location);
        this.monthly_cost = monthly_cost;
        this.number_rooms = number_rooms;
        costumers = new DoublyLinkedList<Student>();
    }

    public double getPrice() { return monthly_cost; }

    public void newCostumer(Student student) {
        costumers.addLast(student);
    }

    public void leavingCostumer(Student student) {
        costumers.remove(find(student.getName()));
    }

    public boolean isFull() {
        return costumers.size() >= number_rooms;
    }

    @Override
    public TwoWayIterator<Student> listStudentsInService() {
        return costumers.twoWayiterator();
    }

    private int find(String name) {
        Iterator<Student> iterator = costumers.iterator();
        int index = 0;
        while(iterator.hasNext()) {
            Student current = iterator.next();
            if (current.getName().equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
