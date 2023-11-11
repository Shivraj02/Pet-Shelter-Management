package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entites.Booking;
import com.smart.entites.PetSitter;
import com.smart.entites.User;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    
    
    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId")
    public List<Booking> findBookingByUser(@Param("userId") int userId);
    
    @Query("SELECT b FROM Booking b WHERE b.petSitter.id = :petsitterId")
    public List<Booking> findBookingByPetsitter(@Param("petsitterId") Long petsitterId);

    @Query("SELECT b FROM Booking b WHERE b.id = :bookingId")
    Booking getBookingById(@Param("bookingId") Long bookingId);

    
    List<Booking> findByPetSitter(PetSitter petSitter);
    
    
}

