package com.AirBnb.TimberAndStone.services;


import com.AirBnb.TimberAndStone.dtos.requests.authentication.RegisterRequest;
import com.AirBnb.TimberAndStone.dtos.requests.user.PatchUserRequest;
import com.AirBnb.TimberAndStone.dtos.responses.authentication.RegisterResponse;
import com.AirBnb.TimberAndStone.dtos.responses.rental.ContactResponse;
import com.AirBnb.TimberAndStone.dtos.responses.user.*;
import com.AirBnb.TimberAndStone.exceptions.ConflictException;
import com.AirBnb.TimberAndStone.exceptions.ResourceNotFoundException;
import com.AirBnb.TimberAndStone.exceptions.UnauthorizedException;
import com.AirBnb.TimberAndStone.models.*;
import com.AirBnb.TimberAndStone.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        // check if username already exists
        if (existsByUsername(registerRequest.getUsername())) {
            throw new ConflictException("Username already exists");
        }
        // check if email already exists
        if (existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }

        // map the authRequest to a User entity
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setPassword(registerRequest.getPassword());

        //Set active to true when account is created.
        user.setActive(true);

        // set rating to 0, 0
        Rating rating = new Rating();
        rating.setAverageRating(0.0);
        rating.setNumberOfRatings(0);
        user.setRating(rating);

        //Sets favorites to new empty array
        user.setFavouriteRentals(new ArrayList<Rental>());

        if(registerRequest.getProfilePhoto() == null || registerRequest.getProfilePhoto().isEmpty()) {
            user.setProfilePhoto("defaultprofile.jpg");
        } else {
            user.setProfilePhoto(registerRequest.getProfilePhoto());
        }

        // create an address
        Address address = new Address();
        address.setCountry(registerRequest.getCountry());
        address.setCity(registerRequest.getCity());
        address.setPostalCode(registerRequest.getPostalCode());
        address.setStreetName(registerRequest.getStreetName());
        address.setStreetNumber(registerRequest.getStreetNumber());
        address.setLatitude(registerRequest.getLatitude());
        address.setLongitude(registerRequest.getLongitude());

        // set users address to created address
        user.setAddress(address);

        // assign roles
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        } else {
            user.setRoles(registerRequest.getRoles());
        }

        // hash password
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // ensure the user has at least default role USER
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Set.of(Role.USER));
        }
        userRepository.save(user);
        return new RegisterResponse("User registered successfully",
                user.getUsername(),
                user.getRoles());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();

    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(this::convertToUserResponse)
                .collect(Collectors.toList());

    }
    public GetSingleUserResponse getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return convertToGetSingleUserResponse(user);
    }

    public UserResponse getUserByEmail(String email) {
        List<UserResponse> userResponses = getAllUsers();
        for (UserResponse userResponse : userResponses) {
            User user = new User();
            user.setUsername(userResponse.getUsername());
            user.setFirstName(userResponse.getFirstName());
            user.setLastName(userResponse.getLastName());
            user.setEmail(userResponse.getEmail());
            if (user.getEmail().equals(email)) {
                return userResponse;
            }
        }
        // if no email is found
            throw new ResourceNotFoundException("User with email " + email + " not found.");

    }
    public ContactResponse getUserContacts(String id) {
        GetSingleUserResponse user = getUserById(id);
        return new ContactResponse("Contact Info:", user.getUsername(), user.getPhoneNumber(), user.getEmail());
    }

    //Activates deactivated users, deactivates activated users.
    public ActivateDeactivateResponse activateDeactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //If user is active, set to false and return deactivated
        if(user.getActive()) {
            user.setActive(false);
            userRepository.save(user);
            return new ActivateDeactivateResponse("User has been deactivated", user.getUsername(), user.getActive());
        }

        //Set active to true and return activated
        user.setActive(true);
        userRepository.save(user);
        return new ActivateDeactivateResponse("User has been activated", user.getUsername(), user.getActive());
    }

    public User getAuthenticated(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    private UserService userService;
    public PatchUserResponse patchUser(String id, PatchUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User currentUser = getAuthenticated();

        if (!currentUser.getId().equals(user.getId())) {
            throw new UnauthorizedException("You do not have permission to change this user!");
        }
        if(request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if(request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if(request.getProfilePhoto() != null) {
            user.setProfilePhoto(request.getProfilePhoto());
        }
        if(request.getFavouriteRentals() != null) {
            user.setFavouriteRentals(request.getFavouriteRentals());
        }
        userRepository.save(user);
        return convertToPatchUserResponse("The user has been updated successfully", user);
    }
    private PatchUserResponse convertToPatchUserResponse(String message, User user) {
        return new PatchUserResponse(
                message,
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRating(),
                user.getProfilePhoto(),
                user.getFavouriteRentals()
        );
    }
// -------------------------------- User Profile ---------------------------------------------
public UserProfileResponse getUserProfileById(String id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return convertToUserProfileResponse(user);
}
    // ---------------------------- User Profile HELP METHODS ---------------------------------------------
    private UserProfileResponse convertToUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getRating(),
                user.getProfilePhoto());

    }
    // ---------------------------- HELP METHODS ---------------------------------------------

    private UserResponse convertToUserResponse(User user) {
        return new UserResponse(
        user.getUsername(),
        user.getFirstName(),
        user.getLastName(),
        user.getEmail());
    }
    private GetSingleUserResponse convertToGetSingleUserResponse(User user) {
        return new GetSingleUserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress().getCountry(),
                user.getAddress().getCity(),
                user.getRating());

}
}

