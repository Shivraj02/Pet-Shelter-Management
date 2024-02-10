package com.smart.service;

import com.smart.entites.PetSitter;

public interface PetSitterService {
    public PetSitter petSitterRegister(PetSitter petSitter, boolean agreement) throws Exception;

}
