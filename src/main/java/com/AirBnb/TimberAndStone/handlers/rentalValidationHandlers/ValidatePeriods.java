package com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;
import com.AirBnb.TimberAndStone.models.Period;

import java.util.List;

public class ValidatePeriods implements RentalValidatorHandler {
    private RentalValidatorHandler nextHandler;

    @Override
    public void handleRequest(RentalRequest request) {
        // Validate that period.start is not equal to or before period.end
        List<Period> periods = request.getAvailablePeriods();
        periods.forEach(period -> {
            if (period.getStartDate().isAfter(period.getEndDate()) || period.getStartDate().equals(period.getEndDate())) {
                throw new IllegalArgumentException("Rental availability period start date must be before end date!");
            }
        });
        if (nextHandler != null) {
            // Next link in chain if one exists
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(RentalValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
