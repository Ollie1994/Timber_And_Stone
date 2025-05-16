package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.requests.booking.BookingRequest;
import com.AirBnb.TimberAndStone.dtos.requests.booking.PatchBookingRequest;
import com.AirBnb.TimberAndStone.dtos.responses.booking.*;
import com.AirBnb.TimberAndStone.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<PostBookingResponse> createBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        PostBookingResponse postBookingResponse = bookingService.createBooking(bookingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(postBookingResponse);
    }


    @GetMapping
    public ResponseEntity<List<AllBookingsResponse>> getBookings() {
        List<AllBookingsResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable String id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.status(HttpStatus.OK).body(booking);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<BookingResponse>> getBookingByUserId(@PathVariable String id) {
        List<BookingResponse> booking = bookingService.getBookingsByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(booking);
    }

    @GetMapping("/user")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        List<BookingResponse> bookings = bookingService.getMyBookings();
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @GetMapping("/rental/{id}")
    public ResponseEntity<List<BookingResponse>> getBookingByRentalId(@PathVariable String id) {
        List<BookingResponse> bookings = bookingService.getBookingsByRentalId(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatchBookingResponse> patchBooking(@PathVariable String id, @RequestBody PatchBookingRequest request) {
        PatchBookingResponse response = bookingService.patchBooking(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/approve/{id}")
    public ResponseEntity<PatchBookingResponse> approveBooking(@PathVariable String id) {
        PatchBookingResponse response = bookingService.approveBooking(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/confirm/{id}")
    public ResponseEntity<PatchBookingResponse> payAndConfirmBooking(@PathVariable String id) {
        PatchBookingResponse response = bookingService.payAndConfirm(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    // ---------------------- Booking profile --------------------------------------------
    @GetMapping("/user/profile/{id}")
    public ResponseEntity<List<BookingProfileResponse>> getBookingsForProfileByUserId(@PathVariable String id) {
        List<BookingProfileResponse> bookings = bookingService.getBookingsForProfileByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookings);
    }

}
