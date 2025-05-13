package com.AirBnb.TimberAndStone.models;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "rentals")
public class Rental {

    @Id
    private String id;

    @NotNull(message = "Title can not be null")
    @NotEmpty(message = "Title can not be empty")
    @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters")
    private String title;

    //@NotNull(message = "Photos can not be null")
    // Changed to max 5 images to match our Figma design
    @Size(max = 5, message = "You can add max 5 photos")
    private List<String> photos;

    @NotNull(message = "Price per night can not be null")
    @Min(value = 1, message = "PricePerNight have to be minimum 1")
    @Max(value = 1000000, message = "PricePerNight have to be max 1 000 000")
    private Double pricePerNight;

    @NotNull(message = "Rating can not be null")
    private Rating rating;

    @DBRef
    @NotNull(message = "Host can not be null")
    private User host;

    @NotNull(message = "Address can not be null")
    private Address address;

    @NotNull(message = "Category can not be null")
    private Category category;

    @NotNull(message = "Amenities can not be null")
    private List<Amenity> amenities;

    @NotNull(message = "Capacity can not be null")
    private Integer capacity;

    @NotNull(message = "Periods can not be null")
    private List<Period> availablePeriods;

    @NotNull(message = "Description can not be null")
    @NotEmpty(message = "Description can not be empty")
    @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters")
    private String description;

    @NotNull(message = "Policy can not be null")
    private String policy;


    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime updatedAt;


//--------------------------------------------- Constructor ------------------------------------------------------------

    public Rental() {
    }


//--------------------------------------------- Getter & Setters -------------------------------------------------------

    public String getId() {
        return id;
    }



    public @NotNull(message = "Title can not be null") @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters") String getTitle() {
        return title;
    }


    public void setTitle(@NotEmpty(message = "Title can not be empty") @Size(min = 1, max = 50, message = "Title has to be between 1-50 characters") String title) {
        this.title = title;
    }

    public @NotNull(message = "Photos can not be null") @Size(max = 5, message = "You can add max 5 photos") List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(@NotNull(message = "Photos can not be null") @Size(max = 5, message = "You can add max 5 photos") List<String> photos) {
        this.photos = photos;
    }

    public @NotNull(message = "Price per night can not be null") @Min(value = 1, message = "PricePerNight have to be minimum 1") @Max(value = 1000000, message = "PricePerNight have to be max 1 000 000") Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(@NotNull(message = "Price per night can not be null") @Min(value = 1, message = "PricePerNight have to be minimum 1") @Max(value = 1000000, message = "PricePerNight have to be max 1 000 000") Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public @NotNull(message = "Rating can not be null") Rating getRating() {
        return rating;
    }

    public void setRating(@NotNull(message = "Rating can not be null") Rating rating) {
        this.rating = rating;
    }

    public @NotNull(message = "Host can not be null") User getHost() {
        return host;
    }

    public void setHost(@NotNull(message = "Host can not be null") User host) {
        this.host = host;
    }

    public @NotNull(message = "Address can not be null") Address getAddress() {
        return address;
    }

    public void setAddress(@NotNull(message = "Address can not be null") Address address) {
        this.address = address;
    }

    public @NotNull(message = "Category can not be null") Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull(message = "Category can not be null") Category category) {
        this.category = category;
    }

    public @NotNull(message = "Amenities can not be null") List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(@NotNull(message = "Amenities can not be null") List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public @NotNull(message = "Capacity can not be null") Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(@NotNull(message = "Capacity can not be null") Integer capacity) {
        this.capacity = capacity;
    }

    public @NotNull(message = "Periods can not be null") List<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(@NotNull(message = "Periods can not be null") List<Period> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public @NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description can not be null") @NotEmpty(message = "Description can not be empty") @Size(min = 1, max = 500, message = "Description has to be between 1-500 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Policy can not be null") String getPolicy() {
        return policy;
    }

    public void setPolicy(@NotNull(message = "Policy can not be null") String policy) {
        this.policy = policy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}