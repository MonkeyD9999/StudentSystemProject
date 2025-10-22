package System;

import java.io.Serializable;

public interface Student extends Serializable {

    public void moveTo(Service service);

    public void evaluate(Service service);

    public void addVisit(Service service);
}
