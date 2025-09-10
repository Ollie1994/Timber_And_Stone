package com.AirBnb.TimberAndStone.services;

import com.AirBnb.TimberAndStone.converters.BookingConverter;
import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.dtos.requests.booking.PatchBookingRequest;
import com.AirBnb.TimberAndStone.dtos.responses.booking.*;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.generators.BookingNumberGenerator;
import com.AirBnb.TimberAndStone.helpers.BookingHelper;
import com.AirBnb.TimberAndStone.helpers.RentalHelper;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import com.AirBnb.TimberAndStone.validation.BookingValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final PeriodService periodService;
    private final RentalService rentalService;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final BookingValidation bookingValidation;
    private final BookingNumberGenerator bookingNumberGenerator;
    private final BookingConverter bookingConverter;
    private final BookingHelper bookingHelper;
    private final RentalHelper rentalHelper;

    public BookingService(BookingRepository bookingRepository, PeriodService periodService, UserService userService, RentalService rentalService, UserRepository userRepository, RentalRepository rentalRepository, BookingValidation bookingValidation, BookingConverter bookingConverter, BookingNumberGenerator bookingNumberGenerator, RentalHelper rentalHelper, BookingHelper bookingHelper) {
         this.bookingRepository = bookingRepository;
        this.periodService = periodService;
        this.userService = userService;
        this.rentalService = rentalService;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.bookingValidation = bookingValidation;
        this.bookingNumberGenerator = bookingNumberGenerator;
        this.bookingConverter = bookingConverter;
        this.bookingHelper = bookingHelper;
        this.rentalHelper = rentalHelper;
    }

    public PostBookingResponse createBooking(BookingRequest bookingRequest) {

        bookingValidation.validateBookingRequest(bookingRequest);

        Booking booking = new Booking();

        //Set user to authorized user.
        booking.setUser(userService.getAuthenticated());

        //Find and set rental
        Rental rental = rentalHelper.getRental(bookingRequest.getRental().getId());

        booking.setRental(rental);

        //DTO values
        Period period = new Period();
        period.setStartDate(bookingRequest.getStartDate());
        period.setEndDate(bookingRequest.getEndDate());
        booking.setPeriod(period);
        booking.setNote(bookingRequest.getNote());
        booking.setNumberOfGuests(bookingRequest.getNumberOfGuests());

        //Autovalues
        booking.setTotalPrice(periodService.getAmountOfDays(period) * rental.getPricePerNight());
        booking.setPaid(false);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        booking.setBookingNumber(bookingNumberGenerator.generateBookingNumber(userService.getAuthenticated().getId(), rental.getId()));
        booking.setReviewedByUser(false);
        booking.setReviewedByHost(false);


        Booking createdBooking = bookingRepository.save(booking);

        return bookingConverter.convertToPostBookingResponse(createdBooking);
    }

    public List<AllBookingsResponse> getAllBookings() {
        //Finds all bookings, converts to DTO and returns list.
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(bookingConverter::convertToAllBookingsResponse)
                .collect(Collectors.toList());
    }

    public BookingResponse getBookingById(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        return bookingConverter.convertToBookingResponse(booking);
    }

    public List<BookingResponse> getBookingsByUserId(String id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(id);

        return bookings.stream()
                .map(bookingConverter::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getMyBookings() {
        User user = userService.getAuthenticated();
        List<Booking> bookings = bookingRepository.findByUserId(user.getId());

        if(bookings.isEmpty()) {
            throw new ResourceNotFoundException("No bookings found");
        }

        return bookings.stream()
                .map(bookingConverter::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public List<BookingResponse> getBookingsByRentalId(String id) {
        rentalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        List<Booking> bookings = bookingRepository.findByRentalId(id);

        return bookings.stream()
                .map(bookingConverter::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    public PatchBookingResponse patchBooking(String id, PatchBookingRequest request) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to change this booking!");
        }

        if(request.getNumberOfGuests() != null) {
            bookingValidation.validateNumberOfGuests(request.getNumberOfGuests(), booking.getRental());
            booking.setNumberOfGuests(request.getNumberOfGuests());
        }

        if(request.getNote() != null) {
            booking.setNote(request.getNote());
        }

        if(request.getStartDate() != null && request.getEndDate() != null) {
            Period period = new Period(request.getStartDate(), request.getEndDate());
            booking.setPeriod(period);
            booking.setTotalPrice(periodService.getAmountOfDays(period) * booking.getRental().getPricePerNight());
        }

        bookingValidation.validateBooking(booking);

        bookingRepository.save(booking);
        return convertToPatchBookingResponse("The booking has been updated successfully", booking);
    }

    public PatchBookingResponse approveBooking(String id) {
        return bookingHelper.approveBooking(id);
    }

    public PatchBookingResponse payAndConfirm(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        User currentUser = userService.getAuthenticated();

        //Check if current user is the booking user
        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to pay and confirm this booking!");
        }

        BookingStatus status = booking.getBookingStatus();

        if (status.equals(BookingStatus.PENDING)) {
            throw new IllegalArgumentException("Booking has to be approved first!");
        } else if (status.equals(BookingStatus.CANCELLED)) {
            throw new IllegalArgumentException("Booking is already cancelled and can not be approved");
        } else if (status.equals(BookingStatus.CONFIRMED) && booking.getPaid()) {
            throw new IllegalArgumentException("Booking is already confirmed and paid!");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setPaid(true);
        bookingRepository.save(booking);
        return new PatchBookingResponse(
                "The booking has been confirmed and paid!",
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

    public void deleteBooking(String id) {
        //Find by id
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        //Get user and check if this is the users booking, else throw ex.
        User currentUser = userService.getAuthenticated();
        if (!currentUser.getId().equals(booking.getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to change this booking!");
        }

        bookingRepository.delete(booking);
    }
    // ------------------------------------ Booking Profile ---------------------------------------------------
    public List<BookingProfileResponse> getBookingsForProfileByUserId(String id){
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Booking> bookings = bookingRepository.findByUserId(id);

        return bookings.stream()
                .map(bookingConverter::convertToBookingProfileResponse)
                .collect(Collectors.toList());
    }
    //------------------------------------------HELP METHODS----------------------------------------------------
    private PatchBookingResponse convertToPatchBookingResponse(String message, Booking booking) {
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