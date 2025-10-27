package System;

import Exceptions.*;
import dataStructures.*;

import java.io.Serializable;

public class StudentSystemClass implements StudentSystem, Serializable {

    private AreaClass currentArea;
    private DoublyLinkedList<Student> students;

    public StudentSystemClass(){
        this.currentArea= null;
        students = new DoublyLinkedList<Student>();
    }


    @Override
    public void createNewArea(String name, long topLeftLat, long topLeftLong, long bottomRightLat, long bottomRightLong) {
        currentArea = new AreaClass(name, topLeftLat, topLeftLong, bottomRightLat, bottomRightLong);
    }

    @Override
    public AreaClass getCurrentArea() {
        return currentArea;
    }

    @Override
    public void addService(String type, String name, int value, long lat, long lng, int price) {
        currentArea.addService(type, name, value, price, new LocationClass(lat, lng));
    }

    @Override
    public Iterator<Service> getServices() {
        return currentArea.getServices().iterator();
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
        Service lodge = currentArea.getLodge(currentLodge);
        if(lodge instanceof LodgeService && !(((LodgeService) lodge).isFull())){
            throw new Error2Exception(lodge.getName());
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
                case "bookish" -> students.addLast(new BookishStudent(name, country, lodge));
                case "outgoing" -> students.addLast(new OutgoingStudent(name, country, lodge));
                case "thrifty" -> students.addLast(new ThriftyStudent(name, country, lodge));
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
    public Iterator<Student> getStudentsAll(String place) {
        if(place.equals("all")){
            Comparator<Student> comparator = new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            };
            SortedDoublyLinkedList<Student> organized = new SortedDoublyLinkedList<>(comparator);
            for(int i=0; i<students.size(); i++){
                organized.add(students.get(i));
            }
            return organized.iterator();
        }
        else{
            Predicate<Student> fromPlace = p ->p.getCountry().equals(place);
            Iterator<Student> temp =  students.iterator();
            return new FilterIterator<Student>(temp, fromPlace);
        }

    }

    @Override
    public void changeLocation(String name, String location) {
        Student student = getStudent(name);
        Service service = getService(location);
        if(location==null){
            throw new Error1Exception(location);
        }
        if(student==null){
            throw new Error2Exception(name);
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

    @Override
    public void changeArea(AreaClass area) {
        this.currentArea = area;
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
        Iterator<Service> it =  currentArea.getServices().iterator();
        while(it.hasNext()){
            Service service = it.next();
            if(service.getName().equals(location)){
                return service;
            }
        }
        return null;
    }


}
