package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart.dao.PetSitterRepository;
import com.smart.entites.PetSitter;

@Service
public class PetSitterServiceImpl implements PetSitterService {

    @Autowired
    private PetSitterRepository petSitterRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public PetSitter petSitterRegister(PetSitter petSitter, boolean agreement) throws Exception {
        if (!agreement) {
            throw new Exception("You have not agreed to the terms and conditions.");
        }
        petSitter.setRole("ROLE_PETSITTER");
        petSitter.setEnabled(true);
        petSitter.setImageUrl("default.png");

        petSitter.setPassword(passwordEncoder.encode(petSitter.getPassword()));
        return petSitterRepository.save(petSitter);
    }

}
