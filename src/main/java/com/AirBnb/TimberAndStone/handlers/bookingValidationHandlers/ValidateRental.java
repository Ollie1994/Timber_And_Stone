package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.helpers.RentalHelper;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;

public class ValidateRental implements BookingValidatorHandler {
    private BookingValidatorHandler nextHandler;
    private final RentalRepository rentalRepository;
    private final RentalHelper rentalHelper;

    public ValidateRental(RentalRepository rentalRepository, RentalHelper rentalHelper) {
        this.rentalRepository = rentalRepository;
        this.rentalHelper = rentalHelper;
    }

    @Override
    public void handleRequest(BookingRequest request) {
        Rental rental = rentalHelper.getRental(request.getRental().getId());

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