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
    public void changeArea(AreaClass area) {
        currentArea=area;
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
        if(lodge instanceof LodgeService && (((LodgeService) lodge).isFull())){
            throw new Error2Exception(lodge.getName());
        }
        if(getStudent(name) != null){
            throw new Error3Exception(lodge.getName());
        }

        switch (type){
            case "bookish" -> students.addLast(new BookishStudent(name, country, type, lodge));
            case "outgoing" -> students.addLast(new OutgoingStudent(name, country, type, lodge));
            case "thrifty" -> students.addLast(new ThriftyStudent(name, country, type, lodge));
        }
        if(lodge instanceof LodgeService){
            ((LodgeService) lodge).newCostumer(students.getLast());
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
        if(students.isEmpty()){
            throw new Error1Exception(place);
        }
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
            Predicate<Student> fromPlace = p ->p.getCountry().equalsIgnoreCase(place);
            Iterator<Student> temp =  students.iterator();
            FilterIterator<Student> filter = new FilterIterator<Student>(temp, fromPlace);
            if(!filter.hasNext()){
               throw new Error1Exception(place);
            }
            return filter;
        }
    }

    @Override
    public void evaluateService(int stars, String location, String description) {
        Service service = getService(location);

        if(service==null)
            throw new Error1Exception(location);

        Rating r = new RatingClass(stars, location, description);
        service.newReview(stars);
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

    @Override
    public Iterator<Service> listVisitedServices(String name) {
        Student student = getStudent(name);

        if (student == null)
            throw new Error1Exception(name);
        if (student instanceof ThriftyStudent)
            throw new Error2Exception(name);
        if (!student.hasVisits())
            throw new Error3Exception(name);

        return student.listVisitedServices();
    }

    @Override
    public Iterator<Service> listServicesByRating() {
        Iterator<Service> it = currentArea.getServices().iterator();
        SortedList<Service> sortedByRating = new SortedDoublyLinkedList<>(new RatingComparator());

        if (it.hasNext())
            throw new Error1Exception("");

        while (it.hasNext()) {
            Service s = it.next();
            sortedByRating.add(s);
        }

        return sortedByRating.iterator();
    }

    @Override
    public Service getStudentCurrentService(String name) {
        if(students.isEmpty()){
            throw new Error1Exception(name);
        }
        Student student = getStudent(name);
        if (student == null)
            throw new Error1Exception(name);
        return student.getCurrentService();
    }

    @Override
    public Student getStudent(String name){

        Iterator<Student> it =  students.iterator();
        while(it.hasNext()){
            Student student = it.next();
            if(student.getName().equalsIgnoreCase(name)){
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
