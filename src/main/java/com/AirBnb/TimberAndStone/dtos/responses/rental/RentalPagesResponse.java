package com.AirBnb.TimberAndStone.dtos.responses.rental;

import com.AirBnb.TimberAndStone.models.*;

import java.util.List;

public class RentalPagesResponse {
    private String id;
    private String title;
    private List<String> photos;
    private Category category;
    private String description;
    private String country;
    private String city;
    private Double averageRating;

    public RentalPagesResponse(String id, String title, List<String> photos, Category category, String description, String country, String city, Double averageRating) {
        this.id = id;
        this.title = title;
        this.photos = photos;
        this.category = category;
        this.description = description;
        this.country = country;
        this.city = city;
        this.averageRating = averageRating;
    }


    public RentalPagesResponse() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
