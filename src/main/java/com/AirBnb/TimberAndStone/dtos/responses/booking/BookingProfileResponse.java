package com.AirBnb.TimberAndStone.dtos.responses.booking;

import com.AirBnb.TimberAndStone.models.BookingStatus;
import com.AirBnb.TimberAndStone.models.Period;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.models.User;

import java.time.LocalDateTime;

public class BookingProfileResponse {
    private String id;
    private String bookingNumber;
    private User user;
    private Integer numberOfGuests;
    private Rental rental;
    private Period period;
    private Double totalPrice;
    private Boolean isPaid;
    private BookingStatus bookingStatus;
    private String note;
    private Boolean reviewedByUser;
    private Boolean reviewedByHost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BookingProfileResponse(String id, String bookingNumber, User user, Integer numberOfGuests, Rental rental, Period period, Double totalPrice, Boolean isPaid, BookingStatus bookingStatus, String note, Boolean reviewedByUser, Boolean reviewedByHost, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.user = user;
        this.numberOfGuests = numberOfGuests;
        this.rental = rental;
        this.period = period;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.bookingStatus = bookingStatus;
        this.note = note;
        this.reviewedByUser = reviewedByUser;
        this.reviewedByHost = reviewedByHost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public BookingProfileResponse() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getReviewedByUser() {
        return reviewedByUser;
    }

    public void setReviewedByUser(Boolean reviewedByUser) {
        this.reviewedByUser = reviewedByUser;
    }

    public Boolean getReviewedByHost() {
        return reviewedByHost;
    }

    public void setReviewedByHost(Boolean reviewedByHost) {
        this.reviewedByHost = reviewedByHost;
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
