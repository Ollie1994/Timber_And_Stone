package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.rental.RentalPageResponse;
import com.AirBnb.TimberAndStone.models.Rental;
import org.springframework.stereotype.Component;


import com.AirBnb.TimberAndStone.dtos.responses.rental.GetRentalsResponse;

@Component
public class RentalConverter {

    public RentalPageResponse convertToRentalPageResponse(Rental rental) {
        return new RentalPageResponse(
                rental.getId(),
                rental.getTitle(),
                rental.getPhotos(),
                rental.getPricePerNight(),
                rental.getRating(),
                rental.getHost(),
                rental.getAddress(),
                rental.getCategory(),
                rental.getAmenities(),
                rental.getCapacity(),
                rental.getAvailablePeriods(),
                rental.getDescription(),
                rental.getPolicy(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()
        );
    }

    public GetRentalsResponse convertToGetRentalsResponse(Rental rental) {
        return new GetRentalsResponse(
                rental.getTitle(),
                rental.getCategory(),
                rental.getCapacity(),
                rental.getPricePerNight(),
                rental.getAddress().getCountry(),
                rental.getAddress().getCity(),
                rental.getRating().getAverageRating());
    }
}
