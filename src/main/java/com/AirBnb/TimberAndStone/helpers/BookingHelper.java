package com.AirBnb.TimberAndStone.helpers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.dtos.responses.booking.PatchBookingResponse;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.generators.BookingNumberGenerator;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.services.PeriodService;
import com.AirBnb.TimberAndStone.services.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingHelper {

    private UserService userService;
    private BookingRepository bookingRepository;
    private BookingNumberGenerator bookingNumberGenerator;
    private PeriodService periodService;

    public BookingHelper(BookingRepository bookingRepository, UserService userService, BookingNumberGenerator bookingNumberGenerator, PeriodService periodService) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.bookingNumberGenerator = bookingNumberGenerator;
        this.periodService = periodService;

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

        // Autovalues
        public void setAutoValues (Booking booking, Period period, Rental rental) {
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setBookingNumber(bookingNumberGenerator.generateBookingNumber(userService.getAuthenticated().getId(), rental.getId()));
        booking.setReviewedByUser(false);
        booking.setReviewedByHost(false);
    }

         // DTO values
        public void setBookingRequestValues (BookingRequest bookingRequest, Booking booking) {
        Period period = new Period();
        period.setStartDate(bookingRequest.getStartDate());
        period.setEndDate(bookingRequest.getEndDate());
        booking.setPeriod(period);
        booking.setNote(bookingRequest.getNote());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());
    }
}
