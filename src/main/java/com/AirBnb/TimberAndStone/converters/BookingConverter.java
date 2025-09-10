package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.booking.BookingProfileResponse;
import com.AirBnb.TimberAndStone.dtos.responses.booking.BookingResponse;
import com.AirBnb.TimberAndStone.models.Booking;
import org.springframework.stereotype.Component;
import com.AirBnb.TimberAndStone.dtos.responses.booking.AllBookingsResponse;

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


    public AllBookingsResponse convertToAllBookingsResponse(Booking booking) {
        return new AllBookingsResponse(
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus()
        );
    }

    public BookingProfileResponse convertToBookingProfileResponse(Booking booking) {
        return new BookingProfileResponse(
                booking.getId(),
                booking.getBookingNumber(),
                booking.getUser(),
                booking.getNumberOfGuests(),
                booking.getRental(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote(),
                booking.getReviewedByUser(),
                booking.getReviewedByHost(),
                booking.getCreatedAt(),
                booking.getUpdatedAt()
        );
    }

}
