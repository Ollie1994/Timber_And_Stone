package com.AirBnb.TimberAndStone.generators;

import com.AirBnb.TimberAndStone.models.Booking;
import com.AirBnb.TimberAndStone.repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class BookingNumberGenerator {
    private final BookingRepository bookingRepo;

    public BookingNumberGenerator(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public String generateBookingNumber(String userID, String rentalID) {
        Integer number = generateRandomNumber();

        // Use a magic number for testing, set a default value and make a booking with the same user and rental twice.
        // Check console for print results. Should return result 1 if none is found and result 2 after re-generating a valid number.
        // number = 1604529367;

        Boolean isUnique = checkUniqueBookingNumber(number.toString(), userID, rentalID);

        if (!isUnique) {
            System.out.println("Booking number already exists in combination with user or rental!");

            while (true) {
                number = generateRandomNumber();
                isUnique = checkUniqueBookingNumber(number.toString(), userID, rentalID);

                if (!isUnique) {
                    System.out.println("Booking number already exists in combination with user or rental!");
                } else {
                    System.out.println("2: No matches found, ID is unique in combination with user and rental");
                    return number.toString();
                }
            }
        }
        System.out.println("1: No matches found, ID is unique in combination with user and rental");
        return number.toString();
    }

    private Integer c() {
        SecureRandom secureRandom = new SecureRandom();
        //Generate random number
        Integer randomPositiveInt = Math.abs(secureRandom.nextInt());
        System.out.println("Generated number: " + randomPositiveInt);

        return randomPositiveInt;
    }

    private Boolean checkUniqueBookingNumber(String boookingNumber, String userID, String rentalID) {
        Booking matchingBooking = bookingRepo.findByBookingNumberAndUserIdAndRentalId(boookingNumber, userID, rentalID)
                .orElse(null);

        //Null means the booking number was not found.
        if(matchingBooking == null) {
            return true;
        } else {
            return false;
        }
    }
}
