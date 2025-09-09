package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.booking.BookingResponse;
import com.AirBnb.TimberAndStone.models.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingConverter {

    public BookingResponse convertToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getBookingNumber(),
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus(),
                booking.getNote(),
                booking.getCreatedAt()
        );
    }
}
