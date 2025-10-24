package System;

import Exceptions.*;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
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
    public void createNewArea(String name, long latMax, long latMin, long lngMax, long lngMin) {
        currentArea = new AreaClass(name, latMax, lngMax, latMin, lngMin);
    }

    @Override
    public Area getCurrentArea() {
        return currentArea;
    }

    @Override
    public void addService(String type, String name, int value, long lat, long lng, int price) {
        currentArea.addService(type, name, value, price, new LocationClass(lat, lng));
    }

    @Override
    public void removeService(String name) {

    }

    @Override
    public boolean isLocationInside(long lat, long lng) {
        return currentArea.isInside(new LocationClass(lat, lng));
    }

    @Override
    public void addStudent(String type, String name, String country, String currentLodge) {
        if(currentArea.getLodge(currentLodge)==null){
            throw new Error1Exception(currentLodge);
        }
        Service s = currentArea.getLodge(currentLodge);
        if(s instanceof LodgeService && !(((LodgeService) s).isFull())){
            throw new Error2Exception(s.getName());
        }
        else{
            Iterator<Student> it =  students.iterator();
            while(it.hasNext()){
                Student student = it.next();
                if(student.getName().equals(name)){
                    throw new Error3Exception(name);
                }
            }

            switch (type){
                case "bookish" -> students.addLast(new BookishStudent(name, country));
                case "outgoing" -> students.addLast(new OutgoingStudent(name, country));
                case "thrifty" -> students.addLast(new ThriftyStudent(name, country));
            }
        }
    }

    @Override
    public void removeStudent(String name) {
        Iterator<Student> it =  students.iterator();
        Student remove = null;
        while (it.hasNext()){
            Student student = it.next();
            if(student.getName().equals(name)){
                remove = student;
            }
        }
        if(remove==null){
            throw new Error1Exception(name);
        }
        else{
            int index = students.indexOf(remove);
            students.remove(index);
        }
    }

    @Override
    public Iterator<Student> getStudents() {
        return students.iterator();
    }
}
