package com.AirBnb.TimberAndStone.helpers;

import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Rating;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import org.springframework.stereotype.Component;

@Component
public class RentalHelper {
    private final RentalRepository rentalRepository;

    public RentalHelper(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

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

    public Rental getRental(String rentalID) {
        return rentalRepository.findById(rentalID)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }
}
