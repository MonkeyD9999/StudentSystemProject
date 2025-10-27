package System;

import dataStructures.TwoWayIterator;

public class LeisureService extends ServiceClass {
    private long ticket_cost;
    private int student_disc;

    public LeisureService(String name, String type, LocationClass location, int ticket_cost, int student_disc) {
        super(name, type, location);
        this.ticket_cost = ticket_cost;
        this.student_disc = student_disc;

    }

    @Override
    public TwoWayIterator<Student> listStudentsInService() {
        return null;
    }
}
