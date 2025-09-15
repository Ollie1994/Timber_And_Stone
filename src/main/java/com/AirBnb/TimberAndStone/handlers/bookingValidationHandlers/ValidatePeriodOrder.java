package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;

public class ValidatePeriodOrder implements BookingValidatorHandler {
    private BookingValidatorHandler nextHandler;

    @Override
    public void handleRequest(BookingRequest request) {

    if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().equals(request.getEndDate())) {
        throw new IllegalArgumentException("Booking period start date must be before end date!");
    } else if (nextHandler != null) {
        nextHandler.handleRequest(request);
    }
    }
    @Override
    public void setNextHandler(BookingValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
