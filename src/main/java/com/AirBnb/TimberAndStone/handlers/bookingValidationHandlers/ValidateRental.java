package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;

public class ValidateRental implements BookingValidatorHandler {
    private BookingValidatorHandler nextHandler;
    private final RentalRepository rentalRepository;

    public ValidateRental(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public void handleRequest(BookingRequest request) {
        Rental rental = rentalRepository.findById(request.getRental().getId()).orElse(null);

        if (rental == null) {
            throw new ResourceNotFoundException("Rental not found");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(BookingValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}