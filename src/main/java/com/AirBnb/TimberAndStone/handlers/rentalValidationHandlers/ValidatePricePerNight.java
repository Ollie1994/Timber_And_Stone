package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

public class ValidatePricePerNight implements RentalValidatorHandler{
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate pricePerNight is between 1-1000000 and not null
        Double pricePerNight = request.getPricePerNight();
        if (pricePerNight == null) {
            throw new IllegalArgumentException("Price per night can not be null");
        } else if (pricePerNight < 1 || pricePerNight > 1000000) {
            throw new IllegalArgumentException("Price per night must be between 1 and 1000000");
        } else if (nextHandler != null) {
            // nästa länk i kedjan ifall det finns en
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
