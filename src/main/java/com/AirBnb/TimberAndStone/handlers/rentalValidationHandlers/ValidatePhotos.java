package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

import java.util.List;

public class ValidatePhotos implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate photos is not more than 5
        List<String> photos = request.getPhotos();
        if (photos != null && photos.size() > 5) {
            throw new IllegalArgumentException("Can not have more than 5 photos");
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
