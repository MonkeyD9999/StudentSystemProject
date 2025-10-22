package System;

import Exceptions.AlreadyExistsObjectException;

import java.io.Serializable;

public interface Area extends Serializable {
    boolean isInside(LocationClass loc);
    void addService(String name, int value, int price, LocationClass loc) throws AlreadyExistsObjectException;
}
