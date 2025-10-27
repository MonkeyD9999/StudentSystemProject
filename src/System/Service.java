package System;

import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface Service extends Serializable {
    String getName();
    LocationClass getLocation();
    String getType();

    TwoWayIterator<Student> listStudentsInService();
}
