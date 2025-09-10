package com.AirBnb.TimberAndStone.helpers;

import com.AirBnb.TimberAndStone.dtos.responses.booking.PatchBookingResponse;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class BookingHelper {

    private UserService userService;
    private BookingRepository bookingRepository;

    public BookingHelper(BookingRepository bookingRepository, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }

    public PatchBookingResponse approveBooking(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        //Check if current user is the host of this rental.
        if (!currentUser.getId().equals(booking.getRental().getHost().getId())) {
            throw new UnauthorizedException("You do not have permission to approve this booking!");
        }

        BookingStatus status = booking.getBookingStatus();

        if (status.equals(BookingStatus.APPROVED) || status.equals(BookingStatus.CONFIRMED)) {
            throw new IllegalArgumentException("Booking is already approved and/or confirmed.");
        }

        if (status.equals(BookingStatus.CANCELLED)) {
            throw new IllegalArgumentException("Booking is already cancelled and can not be approved");
        }

        booking.setBookingStatus(BookingStatus.APPROVED);

        bookingRepository.save(booking);

        return new PatchBookingResponse(
                "The booking has been approved and is now awaiting payment.",
                booking.getRental().getTitle(),
                booking.getBookingNumber(),
                booking.getUser().getUsername(),
                booking.getNumberOfGuests(),
                booking.getPeriod(),
                booking.getTotalPrice(),
                booking.getPaid(),
                booking.getBookingStatus(),
                booking.getNote());
    }
}
