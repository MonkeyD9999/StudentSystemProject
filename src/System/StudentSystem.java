package System;

import java.io.Serializable;

public interface StudentSystem  extends Serializable {

    public void createNewArea(String name, long latTop, long latBottom, long lngTop, long lngBottom);

    public Area getCurrentArea();

    public void addService(String name, int value, long lat, long lng, int price);

    public void removeService(String name);

    public boolean isLocationInside(long lat, long lng);
}
