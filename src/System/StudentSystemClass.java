package System;

import dataStructures.DoublyLinkedList;
import dataStructures.SortedDoublyLinkedList;

import java.io.Serializable;

public class StudentSystemClass implements StudentSystem, Serializable {

    private Area currentArea;
    private DoublyLinkedList<Student> students;

    public StudentSystemClass(){
        this.currentArea= null;
        SortedDoublyLinkedList<Service> services = null;
    }


    @Override
    public void createNewArea(String name, long latTop, long latBottom, long lngRight, long lngLeft) {

    }

    @Override
    public Area getCurrentArea() {
        return currentArea;
    }

    @Override
    public void addService(String name, int value, long lat, long lng, int price) {
        currentArea.addService(name, value, price, new LocationClass(lat, lng));
    }

    @Override
    public void removeService(String name) {

    }

    @Override
    public boolean isLocationInside(long lat, long lng) {
        return currentArea.isInside(new LocationClass(lat, lng));
    }
}
