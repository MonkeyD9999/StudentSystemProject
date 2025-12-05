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

    private SinglyLinkedList<Service> services;
    private SinglyLinkedList<Student> studentsOrder;
    private ClosedHashTable<String,Student>  students;
    private DoublyLinkedList<Service> ratingOrder;

    public AreaClass(String name, long topLeftLat, long topLeftLong, long bottomRightLat, long bottomRightLong) {
        this.name = name;
        this.topLeftLat = topLeftLat;
        this.topLeftLong = topLeftLong;
        this.bottomRightLat = bottomRightLat;
        this.bottomRightLong = bottomRightLong;
        services = new SinglyLinkedList<>();
        studentsOrder = new SinglyLinkedList<>();
        students = new ClosedHashTable<>();
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
        Service service = getService(name);
        if(service != null) {
            throw new AlreadyExistsObjectException(service.getName());
        }

        switch (type) {
            case EAT -> services.addLast( new EatingService(name, type, loc, price, value));
            case LODGE -> services.addLast( new LodgeService(name, type, loc, price, value));
            case LEISURE -> services.addLast( new LeisureService(name, type, loc, price, value));
        }
    }

    @Override
    public Iterator<Service> getServicesAll() {
        return services.iterator();
    }

    @Override
    public void addStudent(String type, String name, String country, String currentLodge) {
        Service lodge = getService(currentLodge);
        Student s = students.get(name.toLowerCase());
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
            case "bookish" -> students.put(name.toLowerCase(),new BookishStudent(name, country, type, lodge));
            case "outgoing" -> students.put(name.toLowerCase(),new OutgoingStudent(name, country, type, lodge));
            case "thrifty" -> students.put(name.toLowerCase(),new ThriftyStudent(name, country, type, lodge));
        }
        studentsOrder.addLast(students.get(name.toLowerCase()));
        ((LodgeService) lodge).newCostumer(students.get(name.toLowerCase()));
    }

    @Override
    public Student removeStudent(String name) {
        Student remove = students.get(name.toLowerCase());
        if(remove==null){
            throw new Error1Exception(name);
        }
        else{
            students.remove(name.toLowerCase());
            Service lodge = remove.getCurrentLodge();
            if(lodge instanceof LodgeService){
                ((LodgeService) lodge).leavingCostumer(remove);
            }
            Service current = remove.getCurrentService();
            if(current instanceof LodgeService && current!=lodge){
                ((LodgeService) current).leavingCostumer(remove);
            }
            if(current instanceof EatingService){
                ((EatingService) current).leaveSeat(remove);
            }
        }

        Iterator<Student> it =  studentsOrder.iterator();
        int index=0;
        while(it.hasNext()){
            Student student = it.next();
            if(student.getName().equalsIgnoreCase(name)){
                studentsOrder.remove(index);
            }
            index++;
        }
        return remove;
    }

    @Override
    public Iterator<Student> getStudentsAll(String place) {
        if(studentsOrder.isEmpty()){
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
            Iterator<Student> iterator = studentsOrder.iterator();
            while(iterator.hasNext()){
                Student student = iterator.next();
                organized.add(student);
            }
            return organized.iterator();
        }
        else{
            Predicate<Student> fromPlace = p ->p.getCountry().equalsIgnoreCase(place);
            Iterator<Student> temp =  studentsOrder.iterator();
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
        Student student = students.get(name.toLowerCase());
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
    public Student changeLodge(String name, String lodge) {
        Student student = students.get(name.toLowerCase());
        Service service = getService(lodge);

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

        return student;
    }

    @Override
    public Service listStudentsInService(String location) {
        Service service = getService(location);

        if(service==null)
            throw new Error1Exception(location);
        if(!(service instanceof LodgeService) && !(service instanceof EatingService))
            throw new Error2Exception(service.getName());

        return service;
    }

    @Override
    public Iterator<Service> listVisitedServices(String name) {
        Student student = students.get(name.toLowerCase());

        if (student == null)
            throw new Error1Exception(name);

        Iterator<Service> it = null;
        if(student instanceof OutgoingStudent){
            it = ((OutgoingStudent)student).listVisitedServices();
        }
        else if(student instanceof BookishStudent){
            it = ((BookishStudent)student).listVisitedServices();
        }
        else{
            throw new Error2Exception(student.getName());
        }

        if (!it.hasNext())
            throw new Error3Exception(student.getName());

        return it;
    }

    @Override
    public Iterator<Service> listServicesByRating() {
        Iterator<Service> it = services.iterator();
        SortedList<Service> sortedByRating = new SortedDoublyLinkedList<>(new RatingComparator(ratingOrder));
        if (!it.hasNext())
            throw new Error1Exception("");

        while (it.hasNext()) {
            Service s = it.next();
            sortedByRating.add(s);
        }

        return sortedByRating.iterator();
    }

    @Override
    public Service getBestService(String name, String type) {
        Student student = students.get(name.toLowerCase());

        if (!(type.equalsIgnoreCase("eating") || type.equalsIgnoreCase("leisure") || type.equalsIgnoreCase("lodging")))
            throw new Error2Exception("");
        if (student == null)
            throw new Error1Exception(name);

        Iterator<Service> it = new FilterIterator<>(services.iterator(),new IsServiceType(type));
        if (!it.hasNext())
            throw new Error3Exception(type);

        // se é thrifty, ver qual o mais barato em ordem de inserção
        if (student instanceof ThriftyStudent) {
            Service best = null;
            while (it.hasNext()) {
                Service s = it.next();
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
                Service s = it.next();
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
        Student student = students.get(name.toLowerCase());

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

        return r.iterator();
    }

    @Override
    public Iterator<Service> listServiceReviewsTagged(String tag) {
        Iterator<Service> it =new FilterIterator<>(services.iterator(), new ReviewHasTag(tag));

        if (!it.hasNext()){
            throw new Error1Exception("");
        }
        return it;
    }

    public Service getService(String name) {
        Iterator<Service> it = services.iterator();
        while (it.hasNext()) {
            Service s = it.next();
            if (s.getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }

    public Student getStudent(String name){
        return students.get(name.toLowerCase());
    }

    /*
    permite ser igual/tar no limite (checar se occorre erro)
     */
    private boolean isInside(LocationClass loc) {
        return loc.getLatitude()<=topLeftLat && loc.getLatitude()>=bottomRightLat &&
                loc.getLongitude()>=topLeftLong && loc.getLongitude()<=bottomRightLong;
    }
}
