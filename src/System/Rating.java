package System;

import java.io.Serializable;

public interface Rating extends  Serializable {
     int getStars();
     StudentClass getStudent();
}
