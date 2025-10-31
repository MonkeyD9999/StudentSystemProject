package System;

import java.io.Serializable;

public interface Location extends Serializable {
    /**
     *
     * @return Latitude
     */
    public long getLatitude();

    /**
     *
     * @return Longitude
     */
    public long getLongitude();
}
