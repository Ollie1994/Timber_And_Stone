package com.AirBnb.TimberAndStone.helpers;

import com.AirBnb.TimberAndStone.models.Rating;
import org.springframework.stereotype.Component;

@Component
public class RentalHelper {
    public Rating getDefaultRating() {
        Rating rating = new Rating();
        rating.setAverageRating(0.0);
        rating.setNumberOfRatings(0);
        return rating;
    }
}
