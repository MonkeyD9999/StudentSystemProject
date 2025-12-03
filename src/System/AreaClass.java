package System;

import Exceptions.*;
import dataStructures.*;

import java.io.*;


public class AreaClass implements Area, Serializable {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    private final String EAT="eating";
    private final String LODGE="lodging";
    private final String LEISURE="leisure";

    private String name;
    private long topLeftLat;
    private long topLeftLong;
    private long bottomRightLat;
    private long bottomRightLong;

    private AVLSortedMap<String, Service> services;
    private SinglyLinkedList<Student> students;
    private DoublyLinkedList<Service> ratingOrder;

    public AreaClass(String name, long topLeftLat, long topLeftLong, long bottomRightLat, long bottomRightLong) {
        this.name = name;
        this.topLeftLat = topLeftLat;
        this.topLeftLong = topLeftLong;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLong = bottomRightLong;
        services = new AVLSortedMap<>();
        students = new SinglyLinkedList<>();
        ratingOrder = new DoublyLinkedList<Service>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException {
        if(!isInside(loc)) {
            throw new Error1Exception("");
        }
        if(services.get(name) != null) {
            throw new AlreadyExistsObjectException(services.get(name).getName());
        }

        switch (type) {
            case EAT -> services.insert(name, new EatingService(name, type, loc, price, value));
            case LODGE -> services.insert(name, new LodgeService(name, type, loc, price, value));
            case LEISURE -> services.insert(name, new LeisureService(name, type, loc, price, value));
        }
    }

    @Override
    public Iterator<Map.Entry<String,Service>> getServicesAll() {
        return services.iterator();
    }

    @Override
    public void addStudent(String type, String name, String country, String currentLodge) {
        Service lodge = services.get(currentLodge);
        Student s = getStudent(name);
        if(!(lodge instanceof LodgeService)){
            throw new Error1Exception(currentLodge);
        }

        if((((LodgeService) lodge).isFull())){
            throw new Error2Exception(lodge.getName());
        }
        if(s != null){
            throw new Error3Exception(s.getName());
        }

        switch (type){
            case "bookish" -> students.addLast(new BookishStudent(name, country, type, lodge));
            case "outgoing" -> students.addLast(new OutgoingStudent(name, country, type, lodge));
            case "thrifty" -> students.addLast(new ThriftyStudent(name, country, type, lodge));
        }
        ((LodgeService) lodge).newCostumer(students.getLast());
    }

    @Override
    public Student removeStudent(String name) {
        Student remove = getStudent(name);
        if(remove==null){
            throw new Error1Exception(name);
        }
        else{
            int index = students.indexOf(remove);
            Student s = students.remove(index);
            Service lodge = s.getCurrentLodge();
            if(lodge instanceof LodgeService){
                ((LodgeService) lodge).leavingCostumer(s);
            }
            Service current = s.getCurrentService();
            if(current instanceof LodgeService && current!=lodge){
                ((LodgeService) current).leavingCostumer(s);
            }
            if(current instanceof EatingService){
                ((EatingService) current).leaveSeat(s);
            }
        }
        return remove;
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
        Service service = services.get(location);

        if(service==null)
            throw new Error1Exception(location);

        int oldAvgRating = service.getAvgRating();

        Rating r = new RatingClass(stars, location, description);
        service.newReview(r);

        if(oldAvgRating!=service.getAvgRating()){
            if(ratingOrder.indexOf(service)==-1){
                ratingOrder.addFirst(service);
            }
            else{
                int index = ratingOrder.indexOf(service);
                ratingOrder.remove(index);
                ratingOrder.addFirst(service);
            }
        }
    }

    @Override
    public boolean changeLocation(String name, String location) {
        Student student = getStudent(name);
        Service service = services.get(location);

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
        Service service = services.get(lodge);

        if (student == null)
            throw new Error1Exception(name);
        if (!(service instanceof LodgeService))
            throw new Error2Exception(lodge);
        if (service.equals(student.getCurrentLodge()))
            throw new Error3Exception(student.getName());
        if (((LodgeService) service).isFull())
            throw new Error4Exception(service.getName());
        if (student instanceof ThriftyStudent && !((ThriftyStudent) student).cheaperLodge(service))
            throw new Error5Exception(student.getName());

        // -- prev Lodge counter, ++ current Lodge counter
        LodgeService current = (LodgeService) student.getCurrentLodge();
        current.leavingCostumer(student);
        ((LodgeService) service).newCostumer(student);
        student.changeLodge(service);
    }

    @Override
    public Service listStudentsInService(String location) {
        Service service = services.get(location);

        if(service==null)
            throw new Error1Exception(location);
        if(!(service instanceof LodgeService) && !(service instanceof EatingService))
            throw new Error2Exception(service.getName());

        return service;
    }

    @Override
    public Iterator<Service> listVisitedServices(String name) {
        Student student = getStudent(name);

        if (student == null)
            throw new Error1Exception(name);
        if (student instanceof ThriftyStudent)
            throw new Error2Exception(student.getName());

        Iterator<Service> it = student.listVisitedServices();
        if (!it.hasNext())
            throw new Error3Exception(student.getName());

        return it;
    }

    @Override
    public Iterator<Service> listServicesByRating() {
        Iterator<Map.Entry<String, Service>> it = services.iterator();
        SortedList<Service> sortedByRating = new SortedDoublyLinkedList<>(new RatingComparator(ratingOrder));
        if (!it.hasNext())
            throw new Error1Exception("");

        while (it.hasNext()) {
            Service s = it.next().value();
            sortedByRating.add(s);
        }

        return sortedByRating.iterator();
    }

    @Override
    public Service getStudentCurrentService(String name) {
        Student student = getStudent(name);
        if (student == null)
            throw new Error1Exception(name);

        return student.getCurrentService();
    }

    @Override
    public Service getBestService(String name, String type) {
        Student student = getStudent(name);

        if (!(type.equalsIgnoreCase("eating") || type.equalsIgnoreCase("leisure") || type.equalsIgnoreCase("lodging")))
            throw new Error2Exception("");
        if (student == null)
            throw new Error1Exception(name);

        Iterator<Map.Entry<String, Service>> it = services.iterator();
        if (!it.hasNext())
            throw new Error3Exception(type);

        // se é thrifty, ver qual o mais barato em ordem de inserção
        if (student instanceof ThriftyStudent) {
            Service best = null;
            while (it.hasNext()) {
                Service s = it.next().value();
                if(best==null){
                    best = s;
                }
                else{
                    double bestPrice = best.getPrice();
                    double currentPrice = s.getPrice();
                    if(s instanceof LeisureService){
                        currentPrice = ((LeisureService) s).getPrice();
                    }
                    if(bestPrice > currentPrice){
                        best = s;
                    }
                }
            }
            return best;
        }
        // senao ver qual tem melhor rating e que o average foi mudado menos recentemente
        else {
            Service best = null;
            while (it.hasNext()) {
                Service s = it.next().value();
                if(best==null){
                    best = s;
                }
                else{
                    if(best.getAvgRating()<s.getAvgRating()){
                        best = s;
                    }
                    else if(best.getAvgRating()==s.getAvgRating()){
                        int indexBest = ratingOrder.indexOf(best);
                        int indexS = ratingOrder.indexOf(s);

                        if(indexBest!=-1 && indexS!=-1 && indexBest<indexS){ // 1 para 1
                            best = s;
                        }
                        else if(indexBest!=-1 && indexS==-1){ // 1 para -1
                            best = s;
                        }

                    }
                }
            }
            return best;
        }

    }

    @Override
    public Iterator<Service> listClosestServiceRanked(int stars, String type, String name) {
        /*Student student = getStudent(name);

        if (student == null)
            throw new Error1Exception(name);
        if (!(type.equalsIgnoreCase("eating") || type.equalsIgnoreCase("leisure") || type.equalsIgnoreCase("lodging")))
            throw new Error2Exception("");

        Iterator<Service> it = new FilterIterator<>(services.iterator(), new IsServiceType(type));
        if (!it.hasNext())
            throw new Error3Exception(type);

        Iterator<Service> iteratorR = new FilterIterator<>(it, new IsRatingStars(stars));
        if (!it.hasNext())
            throw new Error4Exception(type);

        List<Service> r = new DoublyLinkedList<>();
        long minDistance = Long.MAX_VALUE;
        Location studentPos = student.getCurrentService().getLocation();
        while (iteratorR.hasNext()) {
            Service s = iteratorR.next();
            Location servicePos = s.getLocation();

            long distance = Math.abs(studentPos.getLatitude() - servicePos.getLatitude()) + Math.abs(studentPos.getLongitude() - servicePos.getLongitude());
            if(distance < minDistance) {
                while (!r.isEmpty()) {
                    r.removeLast();
                }
                minDistance = distance;
                r.addLast(s);
            }
            else if (distance == minDistance)
                r.addLast(s);
        }

        return r.iterator();*/
        return null;
    }

    @Override
    public Iterator<Map.Entry<String,Service>> listServiceReviewsTagged(String tag) {
        Iterator<Map.Entry<String, Service>> it = services.iterator();

        if (!it.hasNext()){
            throw new Error1Exception("");
        }
        return it;
    }

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

    /*
    permite ser igual/tar no limite (checar se occorre erro)
     */
    private boolean isInside(LocationClass loc) {
        return loc.getLatitude()<=topLeftLat && loc.getLatitude()>=bottomRightLat &&
                loc.getLongitude()>=topLeftLong && loc.getLongitude()<=bottomRightLong;
    }
}
