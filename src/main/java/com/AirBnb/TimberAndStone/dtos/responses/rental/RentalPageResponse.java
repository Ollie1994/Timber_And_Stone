package com.AirBnb.TimberAndStone.dtos.responses.rental;

import com.AirBnb.TimberAndStone.models.*;

import java.time.LocalDateTime;
import java.util.List;

public class RentalPageResponse {
    private String id;
    private String title;
    private List<String> photos;
    private Double pricePerNight;
    private Rating rating;
    private User host;
    private Address address;
    private Category category;
    private List<Amenity> amenities;
    private Integer capacity;
    private List<Period> availablePeriods;
    private String description;
    private String policy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RentalPageResponse(String id, String title, List<String> photos, Double pricePerNight, Rating rating, User host, Address address, Category category, List<Amenity> amenities, Integer capacity, List<Period> availablePeriods, String description, String policy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.photos = photos;
        this.pricePerNight = pricePerNight;
        this.rating = rating;
        this.host = host;
        this.address = address;
        this.category = category;
        this.amenities = amenities;
        this.capacity = capacity;
        this.availablePeriods = availablePeriods;
        this.description = description;
        this.policy = policy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public RentalPageResponse() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(List<Period> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
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