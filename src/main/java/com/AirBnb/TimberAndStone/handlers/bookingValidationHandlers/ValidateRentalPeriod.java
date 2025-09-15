package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.services.PeriodService;

public class ValidateRentalPeriod implements BookingValidatorHandler {
    private BookingValidatorHandler nextHandler;
    private final PeriodService periodService;

    public ValidateRentalPeriod(PeriodService periodService) {
        this.periodService = periodService;
    }
    @Override
    public void handleRequest(BookingRequest request) {
        // periodService.isPeriodMatching checks available periods in a rental
        periodService.isPeriodMatching(request.getRental().getAvailablePeriods(), request);

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(BookingValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
