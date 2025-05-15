package com.AirBnb.TimberAndStone.dtos.responses.authentication;



import com.AirBnb.TimberAndStone.models.Role;

import java.util.Set;

public class AuthResponse {
    private String jwtToken;
    private String username;
    private Set<Role> roles;
    // la till 2025-05-15
    private String id;

    public AuthResponse(String jwtToken, String username, Set<Role> roles, String id) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
        this.id = id;
    }

    public AuthResponse() {
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
