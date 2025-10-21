public class LocationClass implements Location {

    private long lat;
    private long lng;

    public LocationClass(long lat, long lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public double getLatitude() {
        return lat;
    }

    @Override
    public double getLongitude() {
        return lng;
    }
}
