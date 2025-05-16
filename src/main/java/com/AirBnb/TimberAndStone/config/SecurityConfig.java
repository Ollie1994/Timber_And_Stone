package com.AirBnb.TimberAndStone.config;

import com.AirBnb.TimberAndStone.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // create AuthenticationManager which is the "boss" of the authentication process
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // main config for security filter and rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF, disable in dev
                // OBS! should not be disabled in production
                .csrf(csrf -> csrf.disable())
                // define URL based rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        // Vi tog bort "/rental/**" för att ej inloggad användare ska kunna se rentals på home page
                        // Lägg till /** om vi vill gå tillbaka till föregående
                        .requestMatchers("/rental").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/booking/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/conversation/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/userreviews/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/rentalreviews/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/auth/**").permitAll()

                        // added as per chatgpt because of problems displaying the home page rentals
                        .requestMatchers("/rental/page/**").permitAll()
                        // any other requests the user need to be logged
                       .anyRequest().authenticated()
                )
                // disable session due to jwt statelessness
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // add jwt filter before standard filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // cors config
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // only allow request from our future react client
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));


        configuration.setAllowCredentials(true);

        //IFALL OLIWER INTE FÅR ATT FUNKA - //
        //Denna to vi bort efter Helenas cors update. kanske behövs ????
        //configuration.setExposedHeaders(Arrays.asList("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}

