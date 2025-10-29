package System;

import Exceptions.AlreadyExistsObjectException;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Area extends Serializable {
    String getName();

    void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException;
    Iterator<Service> getServicesAll();

    void addStudent(String type, String name, String country, String currentLodge);
    void removeStudent(String name);
    Iterator<Student> getStudentsAll(String place);

    void evaluateService(int star, String location, String description);

    boolean changeLocation(String name, String location);
    void changeLodge(String name, String lodge);

    TwoWayIterator<Student> listStudentsInService(String location);
    Iterator<Service> listVisitedServices(String name);
    Iterator<Service> listServicesByRating();

    Service getStudentCurrentService(String name);
    Service getBestService(String name, String type);

    Iterator<Service> listClosestServiceRanked(int stars, String type, String name);
    Iterator<Service> listServiceReviewsTagged(String tag);

    Student getStudent(String name);

}
