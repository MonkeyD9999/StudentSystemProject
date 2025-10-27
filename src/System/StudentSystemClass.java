package System;

import Exceptions.*;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.SortedDoublyLinkedList;
import dataStructures.TwoWayIterator;

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
        if(getStudent(name) != null){
            throw new Error3Exception(s.getName());
        }

        switch (type){
            case "bookish" -> students.addLast(new BookishStudent(name, country, type));
            case "outgoing" -> students.addLast(new OutgoingStudent(name, country, type));
            case "thrifty" -> students.addLast(new ThriftyStudent(name, country, type));
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
    public boolean changeLocation(String name, String location) {
        Student student = getStudent(name);
        Service service = getService(location);

        if(student==null){
            throw new Error1Exception(name);
        }
        if(service==null){
            throw new Error2Exception(location);
        }
        if(!(service instanceof LeisureService) && !(service instanceof EatingService)){
            throw new Error3Exception(location);
        }
        if(student.getCurrentService().equals(service)) {
            throw new Error4Exception("");
        }
        if(service instanceof EatingService) {
            if (((EatingService) service).isFull())
                throw new Error5Exception(location);
            else
                ((EatingService) service).newCostumer(student);
        }

        // -- prev EatingService seats, ++ current EatingService seats
        Service current = student.getCurrentService();
        if (current instanceof EatingService) { ((EatingService) current).leaveSeat(student); }

        return student.changeLocation(service);
    }

    @Override
    public void changeLodge(String name, String lodge) {
        Student student = getStudent(name);
        Service service = getService(lodge);

        if (student == null)
            throw new Error1Exception(name);
        if (!(service instanceof LodgeService))
            throw new Error2Exception(lodge);
        if (service.equals(student.getCurrentLodge()))
            throw new Error3Exception(name);
        if (((LodgeService) service).isFull())
            throw new Error4Exception(lodge);
        if (student instanceof ThriftyStudent && !((ThriftyStudent) student).cheaperLodge(service))
            throw new Error5Exception(name);

        // -- prev Lodge counter, ++ current Lodge counter
        LodgeService current = (LodgeService) student.getCurrentLodge();
        current.leavingCostumer(student);
        ((LodgeService) service).newCostumer(student);
        student.changeLodge(service);
    }

    @Override
    public TwoWayIterator<Student> listStudentsInService(String location) {
        Service service = getService(location);

        if(service==null)
            throw new Error1Exception(location);
        if(!(service instanceof LodgeService) && !(service instanceof EatingService))
            throw new Error2Exception(location);

        return service.listStudentsInService();
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
