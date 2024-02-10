package com.smart.service;

import java.util.List;

import com.smart.entites.PetSitter;

public interface PetSitterService {
    public PetSitter petSitterRegister(PetSitter petSitter, boolean agreement) throws Exception;
    public List<PetSitter> FindAll();

}
