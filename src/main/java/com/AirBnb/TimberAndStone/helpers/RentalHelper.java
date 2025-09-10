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

    public Rental getRental(String rentalID) {
        return rentalRepository.findById(rentalID)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }
}
