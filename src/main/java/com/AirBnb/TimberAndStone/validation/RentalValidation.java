package com.AirBnb.TimberAndStone.validation;

import com.AirBnb.TimberAndStone.dtos.requests.rental.RentalRequest;
import com.AirBnb.TimberAndStone.handlers.rentalValidationHandlers.*;
import org.springframework.stereotype.Component;

@Component
public class RentalValidation {

    public void validateRentalRequest(RentalRequest request) {
        RentalValidatorHandler validateTitle = new ValidateTitle();
        RentalValidatorHandler validatePhotos = new ValidatePhotos();
        RentalValidatorHandler validatePrice = new ValidatePricePerNight();
        RentalValidatorHandler validateCategory = new ValidateCategory();
        RentalValidatorHandler validateCapacity = new ValidateCapacity();
        RentalValidatorHandler validateDescription = new ValidateDescription();
        RentalValidatorHandler validatePeriods = new ValidatePeriods();

        validateTitle.setNextHandler(validatePhotos);
        validatePhotos.setNextHandler(validatePrice);
        validatePrice.setNextHandler(validateCategory);
        validateCategory.setNextHandler(validateCapacity);
        validateCapacity.setNextHandler(validateDescription);
        validateDescription.setNextHandler(validatePeriods);

        validateTitle.handleRequest(request);
    }

    /*public void validateRentalRequest(RentalRequest request) {
        // Validate title can not be empty or null
        String title = request.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title can not be null or empty");
        }
        // Validate photos is not more than 10
        // Changed to max 5 photos to match our Figma design
        List<String> photos = request.getPhotos();
        if (photos != null && photos.size() > 5) {
            throw new IllegalArgumentException("Can not have more than 5 photos");
        }

        // Validate pricePerNight is between 1-1000000
        Double pricePerNight = request.getPricePerNight();
        if (pricePerNight == null) {
            throw new IllegalArgumentException("Price per night can not be null");
        }
        if (pricePerNight < 1 || pricePerNight > 1000000) {
            throw new IllegalArgumentException("Price per night must be between 1 and 1000000");
        }
        // Validate category is not null
        Category category = request.getCategory();
        if (category == null) {
            throw new IllegalArgumentException("Category can not be null");
        }

        // Validate capacity is at least 1
        Integer capacity = request.getCapacity();
        if (capacity == null || capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }

        // Validate description is not empty or null
        String description = request.getDescription();
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description can not be null or empty");
        }
        // Validate that period.start is not equal to or before period.end
        List<Period> periods = request.getAvailablePeriods();
        periods.forEach(period -> {
            if (period.getStartDate().isAfter(period.getEndDate()) || period.getStartDate().equals(period.getEndDate())) {
                throw new IllegalArgumentException("Rental availability period start date must be before end date!");
            }
        });
    }*/
}



