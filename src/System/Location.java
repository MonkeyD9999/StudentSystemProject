package System;

import java.io.Serializable;

public interface Location extends Serializable {
    public long getLatitude();
    public long getLongitude();
}
