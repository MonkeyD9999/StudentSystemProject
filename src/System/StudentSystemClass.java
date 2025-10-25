package System;

import Exceptions.*;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.SortedDoublyLinkedList;

import java.io.Serializable;

public class StudentSystemClass implements StudentSystem, Serializable {

    private Area currentArea;
    private DoublyLinkedList<Student> students;
    private DoublyLinkedList<Service> services;

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
    public Iterator<Service> getServices() {
        return services.iterator();
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
        Student remove = getStudent(name);
        if(remove==null){
            throw new Error1Exception(name);
        }
        else{
            int index = students.indexOf(remove);
            students.remove(index);
        }
    }

    @Override
    public Iterator<Student> getStudentsAll() {
        return students.iterator();
    }

    @Override
    public void changeLocation(String name, String location) {
        Student student = getStudent(name);
        Service service = getService(location);
        if(student==null){
            throw new Error1Exception(name);
        }
        if(location==null){
            throw new Error2Exception(location);
        }
        if(!(service instanceof LeisureService) && !(service instanceof EatingService)){
            throw new Error3Exception(location);
        }
        if(student.getCurrentService().equals(service)) {
            throw new Error4Exception("");
        }
        if(service instanceof EatingService && ((EatingService) service).isFull()){
            throw new Error5Exception(location);
        }
        student.changeLocation(service);

    }

    private Student getStudent(String name){
        Iterator<Student> it =  students.iterator();
        while(it.hasNext()){
            Student student = it.next();
            if(student.getName().equals(name)){
                return student;
            }
        }
        return null;
    }

    private Service getService(String location){
        Iterator<Service> it =  services.iterator();
        while(it.hasNext()){
            Service service = it.next();
            if(service.getName().equals(location)){
                return service;
            }
        }
        return null;
    }


}
