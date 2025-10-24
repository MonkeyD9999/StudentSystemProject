package System;

import java.io.Serializable;

public interface Service extends Serializable {
    String getName();
    LocationClass getLocation();
    String getType();
}
