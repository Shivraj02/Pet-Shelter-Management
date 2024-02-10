package com.smart.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.Repository.BookingRepository;
import com.smart.Repository.PetRepository;
import com.smart.Repository.PetSitterRepository;
import com.smart.Repository.UserRepository;
import com.smart.entites.Booking;
import com.smart.entites.Pet;
import com.smart.entites.PetSitter;
import com.smart.entites.User;
import com.smart.helper.Message;
import com.smart.service.BookingService;
import com.smart.service.PetSitterService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PetSitterService petSitterService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private PetSitterRepository petSitterRepository;

	public UserController(BookingRepository bookingRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bookingRepository = bookingRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println(username);

		User user = userRepository.getUserByUserName(username);

		System.out.println("User: " + user);
		model.addAttribute(user);
	}

	@RequestMapping("/index")
	public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model, Principal principal) {

		return "normal/user_dashboard";

	}

	@GetMapping("/add_pet")
	public String showAddPetForm(Model model) {
		model.addAttribute("pet", new Pet());
		return "normal/add_pet";
	}

	@PostMapping("/add_pet")
	public String registerPet(@Valid @ModelAttribute("pet") Pet pet, BindingResult result, Model model,
			Principal principal) {
		try {
			if (result.hasErrors()) {
				System.out.println("Errors: " + result.toString());
				model.addAttribute("pet", pet);
				return "normal/add_pet";
			}

			String username = principal.getName();
			User user = userRepository.getUserByUserName(username);

			pet.setUser(user);

			if (pet.getMedicalNeeds() == null || pet.getMedicalNeeds().isEmpty()) {
				pet.setMedicalNeeds("No specific medical needs");
			}

			Pet savedPet = petRepository.save(pet);

			model.addAttribute("pet", new Pet());
			model.addAttribute("message", "Pet successfully registered!");

			return "redirect:/user/MyPets";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("pet", pet);
			model.addAttribute("error", "Something went wrong: " + e.getMessage());
			return "redirect:/user/MyPets";
		}
	}

	@RequestMapping("/MyPets")
	public String MyPets(Model model, Principal principal) {
		model.addAttribute("title", "My Pets");

		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		List<Pet> pets = this.petRepository.findPetsByUser(user.getId());
		model.addAttribute("pets", pets);
		return "normal/MyPets";
	}

	@GetMapping("/edit_pet/{id}")
	public String showEditPetForm(@PathVariable Long id, Model model) {

		Pet pet = petRepository.findById(id).orElse(null);
		if (pet == null) {

			return "redirect:/user/MyPets";
		}

		model.addAttribute("pet", pet);
		return "normal/edit_pet";
	}

	@PostMapping("/edit_pet/{id}")
	public String updatePet(@PathVariable Long id, @Valid @ModelAttribute("pet") Pet updatedPet, BindingResult result,
			Model model) {

		Pet existingPet = petRepository.findById(id).orElse(null);
		if (existingPet == null) {

			return "redirect:/user/MyPets";
		}

		if (result.hasErrors()) {
			model.addAttribute("pet", updatedPet);
			return "normal/edit_pet";
		}

		existingPet.setName(updatedPet.getName());
		existingPet.setAge(updatedPet.getAge());
		existingPet.setBreed(updatedPet.getBreed());
		existingPet.setGender(updatedPet.getGender());
		if (updatedPet.getMedicalNeeds() == null || updatedPet.getMedicalNeeds().isEmpty()) {
			updatedPet.setMedicalNeeds("No specific medical needs");
		}
		existingPet.setMedicalNeeds(updatedPet.getMedicalNeeds());

		if (updatedPet.getMedicalNeeds() == null || updatedPet.getMedicalNeeds().isEmpty()) {
			updatedPet.setMedicalNeeds("No specific medical needs");
		}

		petRepository.save(existingPet);

		return "redirect:/user/MyPets";
	}

	@GetMapping("/delete_pet/{id}")
	public String showDeletePetConfirmation(@PathVariable Long id, Model model) {
		// Load the pet from the database based on the provided id
		Pet pet = petRepository.findById(id).orElse(null);
		if (pet == null) {

			return "redirect:/user/MyPets";
		}

		model.addAttribute("pet", pet);
		return "normal/delete_pet";
	}

	@PostMapping("/delete_pet/{id}")
	public String deletePet(@PathVariable Long id) {

		petRepository.deleteById(id);

		return "redirect:/user/MyPets";
	}

	@GetMapping("/user_booking")
	public String showUserBookings(Model model, @AuthenticationPrincipal User user, Principal principal) {
		// List<Booking> bookings = bookingService.getUserBookings(user);

		String userName = principal.getName();
		User user1 = this.userRepository.getUserByUserName(userName);

		List<Booking> bookings = bookingRepository.findBookingByUser(user1.getId());
		model.addAttribute("bookings", bookings);
		return "normal/user_booking";
	}

	@GetMapping("/create_booking")
	public String showBookingForm(Model model, @AuthenticationPrincipal User user, Principal principal) {
		String userName = principal.getName();
		User user1 = this.userRepository.getUserByUserName(userName);

		model.addAttribute("booking", new Booking());
		model.addAttribute("user", user1);

		// Load petSitters here, assuming you have a method to fetch them
		List<PetSitter> petSitters = petSitterRepository.getAllPetSitters();
		model.addAttribute("petSitters", petSitters);

		// Initialize a PetSitter if necessary
		PetSitter initialPetSitter = new PetSitter(); // You can customize this part
		model.addAttribute("petSitter", initialPetSitter);

		// Load userPets
		List<Pet> userPets = petRepository.findPetsByUserId(user1.getId());
		model.addAttribute("pets", userPets);

		return "normal/create_booking";
	}

	@PostMapping("/create_booking")
	public String createBooking(@ModelAttribute("booking") Booking booking, @AuthenticationPrincipal User user,
			Principal principal) {
		// Set the user and petSitter for the booking
		booking.setUser(user);
		booking.setPetSitter(booking.getPetSitter());
		String username = principal.getName();
		User userByUserName = this.userRepository.getUserByUserName(username);
		booking.setUser(userByUserName);
		// Set the status of the booking
		booking.setStatus("Pending");

		// Save the booking
		bookingService.createBooking(booking);
		bookingRepository.save(booking);

		// Redirect to the user's booking page
		return "redirect:/user/user_booking";
	}

	@GetMapping("/delete_booking/{bookingId}")
	public String showDeleteBookingPage(@PathVariable Long bookingId, Model model) {
		Booking booking = bookingRepository.findById(bookingId).orElse(null);
		if (booking == null) {
			return "redirect:/user/user_booking";
		}
		model.addAttribute("booking", booking);
		return "normal/delete_booking";
	}

	@PostMapping("/delete_booking/{bookingId}")
	public String deleteBooking(@PathVariable Long bookingId) {
		bookingService.deleteBooking(bookingId);
		return "redirect:/user/user_booking";
	}

	@GetMapping("/profile")
	public String yourProfile() {
		return "normal/profile";
	}

	@GetMapping("/PetSitterProfile")
	public String petSitterProfile(Model model) {
		List<PetSitter> petSitter = petSitterService.FindAll();
		model.addAttribute("petsitter", petSitter);
		return "normal/PetSitterProfile";
	}

	@GetMapping("/settings")
	public String showsettings() {
		return "normal/settings";
	}

	@PostMapping("/change_password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
		System.out.println("Old Password" + oldPassword);
		System.out.println("New Password" + newPassword);

		String name = principal.getName();
		User currentUser = this.userRepository.getUserByUserName(name);

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);
			session.setAttribute("message", new Message("Your Password is Successfully Changed!!!", "alert-success"));
		} else {
			session.setAttribute("message", new Message("Please Enter Correct Old Password", "alert-danger"));
		}

		return "redirect:/user/settings";
	}

}
