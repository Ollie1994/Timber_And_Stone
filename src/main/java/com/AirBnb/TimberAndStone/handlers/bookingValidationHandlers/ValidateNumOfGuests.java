package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;


// Checks if the booking requests num of guests is less than 1
// or greater than the rentals' capacity.
public class ValidateNumOfGuests implements BookingValidatorHandler{
    private BookingValidatorHandler nextHandler;



    @Override
    public void handleRequest(BookingRequest request) {
        if (request.getNumberOfGuests() < 1) {
            throw new IllegalArgumentException("Number of guests must be greater than 0");
        } else if (request.getNumberOfGuests() > request.getRental().getCapacity()) {
            throw new IllegalArgumentException("Number of guests must be less than the capacity");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }

    }

    @Override
    public void setNextHandler(BookingValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
