package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.rental.RentalPagesResponse;
import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalConverter {

    public RentalPagesResponse convertToRentalPagesResponse(Rental rental) {
        return new RentalPagesResponse(
                rental.getId(),
                rental.getTitle(),
                rental.getPhotos(),
                rental.getCategory(),
                rental.getDescription(),
                rental.getAddress().getCountry(),
                rental.getAddress().getCity(),
                rental.getRating().getAverageRating());

    }
}
