package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

public class ValidateTitle implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        String title = request.getTitle();
        // Validerar titel
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title can not be null or empty");
        } else if (nextHandler != null) {
            // Går vidare till nästa länk i kedjan ifall en existerar
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
