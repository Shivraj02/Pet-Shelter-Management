package com.smart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.Repository.BookingRepository;
import com.smart.entites.Booking;
import com.smart.entites.PetSitter;
import com.smart.entites.User;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
    
    
    

//    public List<Booking> getAppointmentsByPetSitterName(String petSitterName) {
//        return bookingRepository.findByPetSitter(petSitterName);
//    }
    

}

