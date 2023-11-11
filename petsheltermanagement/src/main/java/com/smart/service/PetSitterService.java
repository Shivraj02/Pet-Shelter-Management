package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.PetSitterRepository;
import com.smart.entites.PetSitter;

@Service
public class PetSitterService {

    // You'll need to inject a repository or data source to interact with your data.
    // Here's an example using a Spring Data JPA repository:
	@Autowired
    private PetSitterRepository petSitterRepository;
    

    @Autowired
    public PetSitterService(PetSitterRepository petSitterRepository) {
        this.petSitterRepository = petSitterRepository;
    }

    // Example service methods

    public PetSitter findById(Long id) {
        return petSitterRepository.findById(id).orElse(null);
    }

    public List<PetSitter> findAll() {
        return petSitterRepository.findAll();
    }

    public PetSitter save(PetSitter petSitter) {
        return petSitterRepository.save(petSitter);
    }
    
    public PetSitter findPetSitterByName(String name) {
        return petSitterRepository.findByName(name);
    }

    // Add more methods as needed for your application

    // You need to define the PetSitterRepository interface and its methods as well.
    // The repository is typically used to interact with your data source, such as a database.

}

