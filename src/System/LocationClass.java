package System;

import java.io.Serializable;

public class LocationClass implements Location, Serializable {

    private long lat;
    private long lng;

    public LocationClass(long lat, long lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public long getLatitude() {
        return lat;
    }

    @Override
    public long getLongitude() {
        return lng;
    }
}
