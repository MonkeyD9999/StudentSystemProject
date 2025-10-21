import dataStructures.DoublyLinkedList;
import dataStructures.SortedDoublyLinkedList;

public class AreaClass implements Area {
    private String name;
    private int max_lat;
    private int max_lng;
    private int min_lat;
    private int min_lng;

    private DoublyLinkedList<Service> services;

    public AreaClass(int max_lat, int max_lng, int min_lat, int min_lng) {
        this.max_lat = max_lat;
        this.max_lng = max_lng;
        this.min_lat = min_lat;
        this.min_lng = min_lng;
        services = new DoublyLinkedList<Service>();
    }

    @Override
    public boolean isInside(LocationClass loc) {
        return loc.getLatitude()>=this.min_lat && loc.getLatitude()<=this.max_lat &&
                loc.getLongitude()>=this.min_lng && loc.getLongitude()<=this.max_lng;
    }

    @Override
    public void addService(Service service) throws DuplicatedObjectException {
        if(services.indexOf(service)!=-1){
            throw new DuplicatedObjectException();
        }
        else {
            services.addLast(service);
        }
    }


}
