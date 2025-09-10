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

    public String getValidatedPolicy (String policy) {
        //If policy is not null...
        if(policy != null) {
            //If empty, return default txt.
            if(policy.trim().isEmpty()) {
                return "Default policy txt";
                //Else, return input policy.
            } else {
                return policy;
            }

        } else {
            //If policy is null
            return "Default policy txt";
        }
    }
}
