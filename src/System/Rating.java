package System;

import java.io.Serializable;

public interface Rating extends  Serializable {
     int getStars();
     String getLocation();
     String getDescription();
}
