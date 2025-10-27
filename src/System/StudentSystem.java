package System;

import java.io.Serializable;
import dataStructures.*;

public interface StudentSystem  extends Serializable {

    public void createNewArea(String name, long latTop, long latBottom, long lngTop, long lngBottom);

    public AreaClass getCurrentArea();
    public void changeArea(AreaClass area);
    public boolean isLocationInside(long lat, long lng);

    public void addService(String type, String name, int value, long lat, long lng, int price);
    public Iterator<Service> getServices();
    public void removeService(String name);


    public void addStudent(String type, String name, String country, String currentLodge);
    public void removeStudent(String name);
    public Iterator<Student> getStudentsAll(String place);

    public void evaluateService(int star, String location, String description);

    public boolean changeLocation(String name, String location);
    public void changeLodge(String name, String lodge);

    public TwoWayIterator<Student> listStudentsInService(String location);
    public Iterator<Service> listVisitedServices(String name);
    public Iterator<Service> listServicesByRating();

    public Service getStudentCurrentService(String name);

    public Student getStudent(String name);

}
