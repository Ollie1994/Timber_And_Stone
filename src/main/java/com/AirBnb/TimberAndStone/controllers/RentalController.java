package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.responses.rental.GetRentalsResponse;
import com.AirBnb.TimberAndStone.dtos.responses.rental.RentalPageResponse;
import com.AirBnb.TimberAndStone.dtos.responses.rental.RentalResponse;
import com.AirBnb.TimberAndStone.models.Category;
import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalAmenitiesRequest;
import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;
import com.AirBnb.TimberAndStone.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rental")
public class RentalController {


    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }


    @PostMapping
    public ResponseEntity<?> createRental(@Valid @RequestBody RentalRequest rentalRequest) {
        RentalResponse rentalResponse = rentalService.createRental(rentalRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalResponse);
    }

    @GetMapping
    public ResponseEntity<List<GetRentalsResponse>> getAllRentals() {
        List<GetRentalsResponse> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRentalsResponse> getRentalById(@Valid @PathVariable String id) {
        GetRentalsResponse rental = rentalService.getRentalById(id);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByCategory(@Valid @PathVariable Category category) {
        List <GetRentalsResponse> rentals = rentalService.getRentalsByCategory(category);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByMinAvgRatingAndMinNumberOfRating(@RequestParam Double minAvgRating, @RequestParam Integer minNumberOfRatings) {
        List<GetRentalsResponse> rentals = rentalService.getRentalsByMinAvgRatingAndMinNumberOfRating(minAvgRating, minNumberOfRatings);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/pricepernight")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByPricePerNightRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<GetRentalsResponse> rentals = rentalService.getRentalsByPricePerNightRange(minPrice, maxPrice);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/availability")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByAvailabilityPeriod(@Valid @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<GetRentalsResponse> rentals = rentalService.getRentalsByAvailabilityPeriod(startDate, endDate);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByCapacity(@PathVariable Integer capacity) {
        List<GetRentalsResponse> response = rentalService.getRentalsByCapacity(capacity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByTitle(@PathVariable String title) {
        List<GetRentalsResponse> response = rentalService.getRentalsByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByCountry(@PathVariable String country) {
        List<GetRentalsResponse> response = rentalService.getRentalsDTOByCountry(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/address/{country}/{city}")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByCountryAndCity(@PathVariable String country, @PathVariable String city) {
        List<GetRentalsResponse> response = rentalService.getRentalsDTOByCountryAndCity(country, city);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetRentalsResponse> patchRentalById(@PathVariable String id, @Valid @RequestBody RentalRequest request) {
        return new ResponseEntity<>(rentalService.patchRentalById(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/amenities")
    public ResponseEntity<List<GetRentalsResponse>>getRentalsByAmenities(@Valid @RequestBody RentalAmenitiesRequest rentalAmenitiesRequest) {
       List<GetRentalsResponse> rentals = rentalService.getRentalsByAmenities(rentalAmenitiesRequest);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/averagerating")
    public ResponseEntity<List<GetRentalsResponse>> getRentalsByAverageRating(@RequestParam Double averageRating) {
        List<GetRentalsResponse> rentals = rentalService.getRentalsByAverageRating(averageRating);
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }


// Rental page ---------------------------------------


    @GetMapping("/page/{id}")
    public ResponseEntity<RentalPageResponse> getRentalPageById(@Valid @PathVariable String id) {
        RentalPageResponse rental = rentalService.getRentalPageById(id);
        return new ResponseEntity<>(rental, HttpStatus.OK);
    }


}











