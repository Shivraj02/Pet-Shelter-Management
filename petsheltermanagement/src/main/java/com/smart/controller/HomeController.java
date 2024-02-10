package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.smart.dao.PetSitterRepository;
import com.smart.dao.UserRepository;
import com.smart.entites.PetSitter;
import com.smart.entites.User;
import com.smart.helper.Message;
import com.smart.service.PetSitterService;
import com.smart.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	private PetSitterService petSitterService;

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home - Pet Shelter Management");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About - Pet Shelter Management");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("title", "Register - Pet Shelter Management");
		model.addAttribute("user", new User());

		return "signup";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
		try {

			if (result.hasErrors()) {
				System.out.println("Errors" + result.toString());
				return "signup";
			}

			User result1 = userService.userRegister(user, agreement);

			model.addAttribute("session", session);
			session.setAttribute("message", new Message("Successfully registered!!!", "alert-success"));

			return "signup";
		} catch (Exception e) {
			e.printStackTrace();

			model.addAttribute("session", session);
			session.setAttribute("message", new Message("Something Went Wrong " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	@GetMapping("/signin")
	public String customLogin(Model model, HttpSession session) {

		model.addAttribute("title", "Login Page");
		model.addAttribute("session", session);
		session.setAttribute("userLoginMessage", new Message("Successfully registered!!!", "alert-success"));

		return "login";
	}

	@RequestMapping("/PetSitterSignUp")
	public String PetSitterSignup(@ModelAttribute PetSitter petSitter, Model model) {
		model.addAttribute("title", "Register as Pet Sitter - Pet Shelter Management");
		return "petSitterSignUp";
	}

	@PostMapping("/Pet_Sitter_do_register")
	public String registerPetSitter(@Valid @ModelAttribute("petSitter") PetSitter petSitter,
			BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			Model model, HttpSession session) {
		try {

			if (result.hasErrors()) {
				System.out.println("Errors " + result.toString());
				return "petSitterSignUp";
			}

			PetSitter resultSitter = petSitterService.petSitterRegister(petSitter, agreement);

			session.removeAttribute("petSitterRegistrationMessage");
			model.addAttribute("session", session);
			model.addAttribute("petSitter", new PetSitter());
			session.setAttribute("petSitterRegistrationMessage",
					new Message("Successfully registered as a Pet Sitter!", "alert-success"));
			return "petSitterSignUp";
		} catch (Exception e) {
			e.printStackTrace();

			model.addAttribute("session", session);
			session.setAttribute("message", new Message("Something Went Wrong: " + e.getMessage(), "alert-danger"));
			return "petSitterSignUp";
		}
	}
}
