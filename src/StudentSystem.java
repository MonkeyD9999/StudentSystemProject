public interface StudentSystem {

    public void createNewArea(String name, int latTop, int latBottom, int lngTop, int lngBottom);

    public Area getCurrentArea();

    public void addService(String name, int latTop, int latBottom, int lngTop, int lngBottom);

    public void removeService(String name);

    public boolean isLocationInside(long lat, long lng);
}
