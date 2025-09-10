package com.AirBnb.TimberAndStone.validation;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.dtos.requests.booking.PatchBookingRequest;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.services.PeriodService;
import org.springframework.stereotype.Component;

@Component
public class BookingValidation {
    private final RentalRepository rentalRepository;
    private final PeriodService periodService;

    public BookingValidation(RentalRepository rentalRepository, PeriodService periodService) {
        this.rentalRepository = rentalRepository;
        this.periodService = periodService;
    }

    public void validateBookingRequest(BookingRequest request) {
        Rental rental = rentalRepository.findById(request.getRental().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        periodService.isPeriodMatching(rental.getAvailablePeriods(), request);

        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().equals(request.getEndDate())) {
            throw new IllegalArgumentException("Booking period start date must be before end date!");
        }

        validateNumberOfGuests(request.getNumberOfGuests(), rental);
    }


    public void validatePatchRequest(PatchBookingRequest request, Booking booking) {

        if (request.getStartDate() != null && request.getEndDate() != null) {
            if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().equals(request.getEndDate())) {
                throw new IllegalArgumentException("Booking period start date must be before end date!");
            }
        }

        if (request.getNumberOfGuests() != null) {
            validateNumberOfGuests(request.getNumberOfGuests(), booking.getRental());
        }
    }


// ---------------------------------------> HELPERS <-----------------------------------------------------------------------


    public void validateNumberOfGuests(int numberOfGuests, Rental rental) {
        if (numberOfGuests < 1) {
            throw new IllegalArgumentException("Number of guests must be greater than 0");
        } else if (numberOfGuests > rental.getCapacity()) {
            throw new IllegalArgumentException("This rental allows max " + rental.getCapacity() + " guests!");
        }
    }

    public void validateBooking(Booking booking) {
        if (booking.getPeriod().getStartDate().isAfter(booking.getPeriod().getEndDate()) || booking.getPeriod().getStartDate().equals(booking.getPeriod().getEndDate())) {
            throw new IllegalArgumentException("Booking period start date must be before end date!");
        }
        validateNumberOfGuests(booking.getNumberOfGuests(), booking.getRental());
    }

}
