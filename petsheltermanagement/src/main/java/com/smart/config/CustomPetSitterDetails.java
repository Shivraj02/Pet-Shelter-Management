package com.smart.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entites.PetSitter;

import java.util.Collection;
import java.util.List;

public class CustomPetSitterDetails implements UserDetails {

    private PetSitter petSitter;

    public CustomPetSitterDetails(PetSitter petSitter) {
        super();
        this.petSitter = petSitter;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(petSitter.getRole());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return petSitter.getPassword();
    }

    @Override
    public String getUsername() {
        return petSitter.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return petSitter.isEnabled();
    }
}
