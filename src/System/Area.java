package System;

import Exceptions.AlreadyExistsObjectException;
import dataStructures.Iterator;
import dataStructures.Map;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Area extends Serializable {
    /**
     *
     * @return Name of the area
     */
    String getName();

    /**
     *
     * @param type Type of tthe service (Lodge, Leisure or Eating)
     * @param name Name of the service
     * @param value Value of the service (Number of seats/rooms ou student discount)
     * @param price Price of using the service
     * @param loc   Location of the service
     * @throws AlreadyExistsObjectException
     */
    void addService(String type, String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException;

    /**
     *
     * @return Iterator with all the services
     */
    Iterator<Map.Entry<String,Service>> getServicesAll();

    /**
     *
     * @param type Type of the student (Bookish, Outgoing or Thrifty)
     * @param name Name of the student
     * @param country Country of the student
     * @param currentLodge Lodge service the student is staying at
     */
    void addStudent(String type, String name, String country, String currentLodge);

    /**
     *
     * @param name Name of the student to remove
     * @return The removed student
     */
    Student removeStudent(String name);

    /**
     *
     * @param place The place of the students (all or a country)
     * @return Iterator with the chosen students
     */
    Iterator<Student> getStudentsAll(String place);

    /**
     *
     * @param star Number of stars for the service
     * @param location Name of the service
     * @param description Description about the evaluation
     */
    void evaluateService(int star, String location, String description);

    /**
     *
     * @param name Name of the student
     * @param location Name of the new service
     * @return true if thrifty student is distracted
     */
    boolean changeLocation(String name, String location);

    /**
     *
     * @param name Name of the student
     * @param lodge Name of the new Lodge service he will be staying at
     */
    void changeLodge(String name, String lodge);

    /**
     *
     * @param location Name of a service
     * @return Iterator with all the students in the service
     */
    Service listStudentsInService(String location);

    /**
     *
     * @param name Name of a student
     * @return Iterator with all the visited services of the student
     */
    Iterator<Service> listVisitedServices(String name);

    /**
     *
     * @return Iterator with all the services with a descending order of their average rating
     */
    Iterator<Service> listServicesByRating();

    /**
     *
     * @param name Name of the student
     * @return Service the student is currently at
     */
    Service getStudentCurrentService(String name);

    /**
     *
     * @param name Name of a student
     * @param type Type of a service
     * @return Service of the type most suited for the student
     */
    Service getBestService(String name, String type);

    /**
     *
     * @param stars Average rating wanted
     * @param type Type of the service wanted
     * @param name Name of the student
     * @return Iterator with the closest services of the type with the wanted average rating
     */
    Iterator<Service> listClosestServiceRanked(int stars, String type, String name);

    /**
     *
     * @param tag Description of a rating
     * @return Iterator with the services that have a rating with a description that have the tag
     */
    Iterator<Map.Entry<String, Service>> listServiceReviewsTagged(String tag);

    /**
     *
     * @param name Name of a student
     * @return Student or -1 if doesn't exists
     */
    Student getStudent(String name);
}
