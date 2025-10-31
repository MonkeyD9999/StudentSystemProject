package System;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

public class EatingService extends ServiceClass {
    private long price;
    private int number_seats;
    private DoublyLinkedList<Student> costumers;

    public EatingService(String name, String type, LocationClass location, int price, int number_seats) {
        super(name, type, location);
        this.price = price;
        this.number_seats = number_seats;
        costumers = new DoublyLinkedList<Student>();
    }

    public double getPrice() { return price; }

    public boolean isFull(){ return costumers.size() >= number_seats; }

    public void newCostumer(Student newStudent) { costumers.addLast(newStudent); }
    public void leaveSeat(Student student) { costumers.remove(find(student.getName())); }



    public TwoWayIterator<Student> listStudentsInService() { return costumers.twoWayiterator(); }

    public void readCustomers(DoublyLinkedList<Student> customers) {
        this.costumers = customers;
    }

    private int find(String name) {
        Iterator<Student> iterator = costumers.iterator();
        int index = 0;
        while(iterator.hasNext()) {
            Student current = iterator.next();
            if (current.getName().equalsIgnoreCase(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
