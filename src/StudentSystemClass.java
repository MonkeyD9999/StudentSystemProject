import dataStructures.DoublyLinkedList;
import dataStructures.SortedDoublyLinkedList;

import java.security.Provider;

public class StudentSystemClass implements StudentSystem{

    private Area currentArea;
    private SortedDoublyLinkedList<Service> services;
    private DoublyLinkedList<Student> students;

    public StudentSystemClass(){
        this.currentArea= null;
        SortedDoublyLinkedList<Service> services = null;
    }


    @Override
    public void createNewArea(String name, int latTop, int latBottom, int lngTop, int lngBottom) {

    }

    @Override
    public Area getCurrentArea() {
        return currentArea;
    }

    @Override
    public void addService(String name, int latTop, int latBottom, int lngTop, int lngBottom) {

    }

    @Override
    public void removeService(String name) {

    }

    @Override
    public boolean isLocationInside(long lat, long lng) {
        return currentArea.isInside(new LocationClass(lat, lng));
    }
}
