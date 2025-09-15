package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;
import com.AirBnb.TimberAndStone.models.Category;

public class ValidateCategory implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate category is not null
        Category category = request.getCategory();
        if (category == null) {
            throw new IllegalArgumentException("Category can not be null");
        } else if (nextHandler != null) {
            // nästa länk i kedja ifall en finns
            nextHandler.handleRequest(request);
        }

    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
