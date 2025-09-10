package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.booking.AllBookingsResponse;
import com.AirBnb.TimberAndStone.models.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {


    public AllBookingsResponse convertToAllBookingsResponse(Booking booking) {
        return new AllBookingsResponse(
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus()
        );
    }


}
