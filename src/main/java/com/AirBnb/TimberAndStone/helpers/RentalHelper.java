package com.AirBnb.TimberAndStone.helpers;

import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.models.Amenity;
import com.AirBnb.TimberAndStone.models.Address;
import com.AirBnb.TimberAndStone.models.Rating;
import com.AirBnb.TimberAndStone.models.Rental;
import com.AirBnb.TimberAndStone.repositories.RentalRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalHelper {
    private final RentalRepository rentalRepository;

    public RentalHelper(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rating getDefaultRating() {
        Rating rating = new Rating();
        rating.setAverageRating(0.0);
        rating.setNumberOfRatings(0);
        return rating;
    }

    public String getValidatedPolicy (String policy) {
        //If policy is not null...
        if(policy != null) {
            //If empty, return default txt.
            if(policy.trim().isEmpty()) {
                return "Default policy txt";
                //Else, return input policy.
            } else {
                return policy;
            }

        } else {
            //If policy is null
            return "Default policy txt";
        }
    }

    public Rental getRental(String rentalID) {
        return rentalRepository.findById(rentalID)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));
    }

    public boolean isAmenitiesMatching(List<Amenity> amenitiesRental, List<Amenity> amenitiesDTO) {
        Boolean match = false;
        Integer count = 0;
        if (amenitiesRental.size() < amenitiesDTO.size()) {
            System.out.println(amenitiesRental.size() + " (>=) " + amenitiesDTO.size());
            System.out.println("--------------------------------------------------------");
            return match = false;
        }
        for (Amenity amenityDTO : amenitiesDTO) {
            for (Amenity amenity : amenitiesRental) {
                if (amenity.getAmenityTitle().equals(amenityDTO.getAmenityTitle())) {
                    System.out.println(amenity.getAmenityTitle() + " ---> MATCH <--- " + amenityDTO.getAmenityTitle());
                    match = true;
                    count++;
                    System.out.println("-> " + count + " & True <-");
                    if (count == amenitiesDTO.size()) {
                        System.out.println("-> Break & " + count + " <-");
                        break;
                    }
                } else if (count != amenitiesDTO.size()) {
                    System.out.println("-> No Match & False <-");
                    match = false;
                }
            }
        }
        System.out.println("--------------------------------------------------------");
        return match;
    }


    // Command pattern to have two trimming-methods (for country and city)
    // https://stackoverflow.com/questions/2186931/java-pass-method-as-parameter
    private interface RentalCommand {
        void execute(Rental rental);
    }

    private static class trimCountry implements RentalCommand {
        @Override
        public void execute(Rental rental) {
                Address address = rental.getAddress();
                rental.setAddress(new Address(
                        StringUtils.trimAllWhitespace(address.getCountry()),
                        address.getCity(),
                        address.getPostalCode(),
                        address.getStreetName(),
                        address.getStreetNumber(),
                        address.getLatitude(),
                        address.getLongitude()));
        }
    }

    private static class trimCity implements RentalCommand {
        @Override
        public void execute(Rental rental) {
                Address address = rental.getAddress();
                rental.setAddress(new Address(
                        address.getCountry(),
                        StringUtils.trimAllWhitespace(address.getCity()),
                        address.getPostalCode(),
                        address.getStreetName(),
                        address.getStreetNumber(),
                        address.getLatitude(),
                        address.getLongitude()));
        }
    }

    private static List<Rental> callTrimCommand(RentalCommand command, List<Rental> rentals) {
        for(Rental rental : rentals) {
            command.execute(rental);
        }
        return rentals;
    }

  /*  private List<Rental> trimCountryNamesFromRentalList(List<Rental> rentalList) {
        for(Rental rental : rentalList) {
            Address address = rental.getAddress();
            rental.setAddress(new Address(
                    StringUtils.trimAllWhitespace(address.getCountry()),
                    address.getCity(),
                    address.getPostalCode(),
                    address.getStreetName(),
                    address.getStreetNumber(),
                    address.getLatitude(),
                    address.getLongitude()));
        }
        return rentalList;
    }

    private List<Rental> trimCityNamesFromRentalList(List<Rental> rentalList) {
        for(Rental rental : rentalList) {
            Address address = rental.getAddress();
            rental.setAddress(new Address(
                    address.getCountry(),
                    StringUtils.trimAllWhitespace(address.getCity()),
                    address.getPostalCode(),
                    address.getStreetName(),
                    address.getStreetNumber(),
                    address.getLatitude(),
                    address.getLongitude()));
        }
        return rentalList;
    }*/

    public List<Rental> getRentalsByCountry(String country) {
        //Trim whitespace from country input
        String trimmedCountry = StringUtils.trimAllWhitespace(country);

        //Make a list of rentals with trimmed country names.
        List<Rental> allRentals = rentalRepository.findAll();

       // List<Rental> trimmedRentals = trimCountryNamesFromRentalList(allRentals);
        List<Rental> trimmedRentals = callTrimCommand(new trimCountry(), allRentals);

        //Filter only matching trimmed rentals to trimmed country
        trimmedRentals = trimmedRentals.stream()
                .filter(rental -> rental.getAddress().getCountry().equalsIgnoreCase(trimmedCountry))
                .toList();

        //New list for holding matching untrimmed rentals, to output the untrimmed country.
        List<Rental> matchingRentals = new ArrayList<>();

        //For each rental.id, compare to trimmedRental.id and add to matchingRentals.
        List<Rental> rentals = rentalRepository.findAll();
        for (Rental rental : rentals) {
            for (Rental trimmedRental : trimmedRentals) {
                if(rental.getId().equals(trimmedRental.getId())) {
                    matchingRentals.add(rental);
                }
            }
        }
        return matchingRentals;
    }

    public List<Rental> getRentalsByCountryAndCity(String country, String city) {
        //Trim all city names
        String trimmedCity = StringUtils.trimAllWhitespace(city);

        //Make a list of matching country rentals with trimmed city names.
        List<Rental> rentalsByCountry = getRentalsByCountry(country);

        List<Rental> trimmedRentals = callTrimCommand(new trimCountry(), rentalsByCountry);
        trimmedRentals = callTrimCommand(new trimCity(), trimmedRentals);

        //Filter only matching trimmed rentals to trimmed city
        trimmedRentals = trimmedRentals.stream()
                .filter(rental -> rental.getAddress().getCity().equalsIgnoreCase(trimmedCity))
                .toList();

        //New list for holding matching untrimmed rentals (to output the untrimmed city.)
        List<Rental> matchingRentals = new ArrayList<>();

        //For each rental.id, compare to trimmedRental.id and add to matchingRentals.
        List<Rental> rentals = rentalRepository.findAll();
        for (Rental rental : rentals) {
            for (Rental trimmedRental : trimmedRentals) {
                if(rental.getId().equals(trimmedRental.getId())) {
                    matchingRentals.add(rental);
                }
            }
        }
        return matchingRentals;
    }
}
