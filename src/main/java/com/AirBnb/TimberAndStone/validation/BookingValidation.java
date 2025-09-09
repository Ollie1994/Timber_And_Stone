package com.AirBnb.TimberAndStone.validation;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.services.PeriodService;

public class BookingValidation {
    private final RentalRepository rentalRepository;
    private final PeriodService periodService;

    public BookingValidation(RentalRepository rentalRepository, PeriodService periodService) {
        this.rentalRepository = rentalRepository;
        this.periodService = periodService;
    }

    public void validateBooking(BookingRequest request) {
        Rental rental = rentalRepository.findById(request.getRental().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        periodService.isPeriodMatching(rental.getAvailablePeriods(), request);

        if(request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().equals(request.getEndDate())) {
            throw new IllegalArgumentException("Booking period start date must be before end date!");
        }

        if (request.getNumberOfGuests() < 1) {
            throw new IllegalArgumentException("Number of guests must be greater than 0");
        } else if (request.getNumberOfGuests() > rental.getCapacity()) {
            throw new IllegalArgumentException("This rental allows max " + rental.getCapacity() + " guests!");
        }
    }
}
