package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;

public interface RentalValidatorHandler {
    void handleRequest(RentalRequest request);
    void setNextHandler(RentalValidatorHandler nextHandler);
}

