package com.smart.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.Repository.BookingRepository;
import com.smart.Repository.PetSitterRepository;
import com.smart.entites.Booking;
import com.smart.entites.PetSitter;

@Controller
@RequestMapping("/pet_sitter")
public class PetSitterController {

	@Autowired
	private PetSitterRepository petSitterRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println(username);

		PetSitter petSitter = petSitterRepository.getPetSitterByUserName(username);

		System.out.println("PetSitter: " + petSitter);
		model.addAttribute("petSitter", petSitter);
	}

	@RequestMapping("/index")
	public String petSitterDashboard() {

		return "petsitter/petsitter_dashboard";

	}

	@GetMapping("/profile")
	public String showProfile() {

		return "petsitter/profile";
	}

	@GetMapping("/appointments")
	public String showAppointments(Model model, Principal principal) {
		String userName = principal.getName();
		PetSitter petsitter = this.petSitterRepository.getPetSitterByUserName(userName);
		System.out.println(petsitter);
		System.out.println("id : " + petsitter.getId());

		List<Booking> bookings = bookingRepository.findBookingByPetsitter(petsitter.getId());
		List<Booking> pendingBookings = new ArrayList<>();

		for (Booking booking : bookings) {
			if ("Pending".equals(booking.getStatus())) {
				pendingBookings.add(booking);
			}
		}
		System.out.println("bookings" + bookings);
		model.addAttribute("bookings", bookings);
		return "petsitter/appointments";

	}

	@GetMapping("/accept_booking/{bookingId}")
	public String acceptBooking(@PathVariable Long bookingId) {
		Booking booking = bookingRepository.getBookingById(bookingId);
		if (booking != null) {
			booking.setStatus("Approved");
			bookingRepository.save(booking);
		}
		return "redirect:/pet_sitter/appointments";
	}

	@GetMapping("/reject_booking/{bookingId}")
	public String rejectBooking(@PathVariable Long bookingId) {
		Booking booking = bookingRepository.getBookingById(bookingId);
		if (booking != null) {
			booking.setStatus("Denied");
			bookingRepository.save(booking);
		}
		return "redirect:/pet_sitter/appointments";
	}

}
