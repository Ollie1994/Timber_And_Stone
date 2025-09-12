package com.AirBnb.TimberAndStone.handlers.bookingValidationHandlers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;

public interface BookingValidatorHandler {
    void handleRequest(BookingRequest request);
    void setNextHandler(BookingValidatorHandler nextHandler);
}
