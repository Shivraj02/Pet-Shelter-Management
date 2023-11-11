package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.dao.PetSitterRepository;
import com.smart.entites.PetSitter;

@Service
public class PetSitterDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PetSitterRepository petSitterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PetSitter petSitter = petSitterRepository.getPetSitterByUserName(username);

        if (petSitter == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        CustomPetSitterDetails petSitterDetails = new CustomPetSitterDetails(petSitter);

        return petSitterDetails;
    }
}
