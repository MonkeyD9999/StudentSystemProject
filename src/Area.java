public interface Area {
    boolean isInside(LocationClass loc);
    void addService(Service service) throws DuplicatedObjectException;
}
