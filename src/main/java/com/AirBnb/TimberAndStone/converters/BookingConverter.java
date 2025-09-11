package com.AirBnb.TimberAndStone.converters;

import com.AirBnb.TimberAndStone.dtos.responses.booking.*;
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

    public AllBookingsResponse convertToAllBookingsResponse(Booking booking) {
        return new AllBookingsResponse(
                booking.getRental().getTitle(),
                booking.getUser().getUsername(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getBookingStatus()
        );
    }

    public PostBookingResponse convertToPostBookingResponse(Booking booking) {

      return new PostBookingResponse(
              "Rental has been booked successfully",
              booking.getRental().getTitle(),
              booking.getPeriod(),
              booking.getTotalPrice(),
              booking.getNote(),
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

    public PatchBookingResponse convertToPatchBookingResponse(String message, Booking booking) {
        return new PatchBookingResponse(
                message,
                booking.getRental().getTitle(),
                booking.getBookingNumber(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote()
        );
    }
}
