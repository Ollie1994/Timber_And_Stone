package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

public class ValidateCapacity implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate capacity is at least 1
        Integer capacity = request.getCapacity();
        if (capacity == null || capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        } else if (nextHandler != null) {
            // nästa länk i kedjan ifall en finns
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
