package com.smart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smart.entites.Pet;


public interface PetRepository extends CrudRepository<Pet, Long> {
    // Define custom queries or methods if needed
	@Query("from Pet as c where c.user.id=:userId")
	public List<Pet> findPetsByUser(@Param("userId") int userId);
	
	 @Query("SELECT p FROM Pet p WHERE p.user.id = :userId")
	 List<Pet> findPetsByUserId(@Param("userId") int i);
}

