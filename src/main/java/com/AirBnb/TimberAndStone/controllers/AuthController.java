package com.AirBnb.TimberAndStone.controllers;

import com.AirBnb.TimberAndStone.dtos.requests.authentication.AuthRequest;
import com.AirBnb.TimberAndStone.dtos.requests.authentication.RegisterRequest;
import com.AirBnb.TimberAndStone.dtos.responses.authentication.AuthResponse;
import com.AirBnb.TimberAndStone.dtos.responses.authentication.RegisterResponse;
import com.AirBnb.TimberAndStone.models.User;
import com.AirBnb.TimberAndStone.services.UserService;
import com.AirBnb.TimberAndStone.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            // authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            // set authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // get User details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // generate JWT token
            String jwt = jwtUtil.generateToken(userDetails);
            ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                    .httpOnly(true) // prevents javascript to get cookie
                    .secure(false) // IMPORTANT TO CHANGE IN PRODUCTION TO TRUE
                    .path("/") // cookies is available in all application
                    .maxAge(10 * 60 * 60) // valid for 10h
                    .sameSite("Strict") // Lax & None
                    .build();
            // create response object
            AuthResponse authResponse = new AuthResponse(
                    "Login successful",
                    userDetails.getUsername(),
                    userService.findByUsername(userDetails.getUsername()).getRoles(),
                    // la till 2025-05-15
                    userService.findByUsername(userDetails.getUsername()).getId()
            );
            // return response with cookie-header and body
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(authResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // skapa en utgången cookie för att ersätta den befintliga jwt cookien
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) // VIKTIGT! ändra i production
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        // response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        // rensa securitykontexten
        SecurityContextHolder.clearContext();

        // returnera svar med utgången cookie
        return ResponseEntity.ok()
                // hade kunnat ta bort denna raden och använda rad 134
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Logout successful!");
    }



// SLÄNGER IN DENNA FÖR ATT vår frontend vill ha en sådan (KAN TAS BORT)

    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // kontrollera om användaren är authenticated
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated!");
        }

        // returnera user info om authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(
                "Authenticated",
                user.getUsername(),
                user.getRoles(),
                // la till 2025-05-15
                user.getId()
        ));
    }





}
