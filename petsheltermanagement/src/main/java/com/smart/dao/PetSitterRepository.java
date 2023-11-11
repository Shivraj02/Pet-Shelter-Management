package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entites.PetSitter;


@Repository
public interface PetSitterRepository extends JpaRepository<PetSitter, Long> {
    // You can add custom queries or methods here if needed.
	@Query("select ps from PetSitter ps where ps.email = :email")
	public PetSitter getPetSitterByUserName(@Param("email") String email);

	
	 @Query("SELECT p FROM PetSitter p")
	    List<PetSitter> getAllPetSitters();
	 
	@Query("FROM PetSitter ps WHERE ps.user.id = :userId")
    PetSitter findPetSitterByUserId(@Param("userId") Long userId);
	
	@Query("SELECT p FROM PetSitter p WHERE p.name = :name")
    PetSitter findByName(@Param("name") String name);
	
	
	PetSitter findByEmail(String email);

}
