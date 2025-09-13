package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

public class ValidateDescription implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate description is not empty or null
        String description = request.getDescription();
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description can not be null or empty");
        } else if (nextHandler != null) {
            // next link in chain if one exists
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
