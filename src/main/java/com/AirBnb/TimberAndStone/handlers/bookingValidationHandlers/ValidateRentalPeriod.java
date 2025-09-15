package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.helpers.RentalHelper;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.services.PeriodService;

public class ValidateRentalPeriod implements BookingValidatorHandler {
    private BookingValidatorHandler nextHandler;
    private final PeriodService periodService;
    private final RentalHelper rentalHelper;

    public ValidateRentalPeriod(PeriodService periodService, RentalHelper rentalHelper) {
        this.periodService = periodService;
        this.rentalHelper = rentalHelper;
    }
    @Override
    public void handleRequest(BookingRequest request) {
        // periodService.isPeriodMatching checks available periods in a rental
        Rental rental = rentalHelper.getRental(request.getRental().getId());
        periodService.isPeriodMatching(rental.getAvailablePeriods(), request);

        if (nextHandler != null) {
            nextHandler.handleRequest(request);
        }
    }

    @Override
    public void setNextHandler(BookingValidatorHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

}
