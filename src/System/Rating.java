package System;

import java.io.Serializable;

public interface Rating extends  Serializable {
    /**
     *
     * @return average rating
     */
     int getStars();

    /**
     *
     * @return Description of the rating
     */
     String getDescription();
}
