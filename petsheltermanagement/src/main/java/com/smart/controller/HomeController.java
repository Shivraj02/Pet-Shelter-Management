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
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.PetSitterRepository;
import com.smart.dao.UserRepository;
import com.smart.entites.Contact;
import com.smart.entites.PetSitter;
import com.smart.entites.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
    private PasswordEncoder passwordEncoder2; // You need to inject the password encoder bean.

    @Autowired
    private PetSitterRepository petSitterRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; 
	
	@Autowired
	private UserRepository userRepository;
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
		model.addAttribute("user",new User());
		
		return "signup";
	}
	
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
	    try {
	        if (!agreement) {
	            System.out.println("You have not agreed the terms and conditions");
	            throw new Exception("You have not agreed the terms and conditions");
	        }
	        
	        if(result1.hasErrors())
	        {
	        	System.out.println("Errors"+result1.toString());
	        	model.addAttribute("user",user);
	        	return "signup";
	        }
	        
	        System.out.println("User "+user);
	        System.out.println("Agreement " + agreement);
	        
	        user.setRole("ROLE_USER");
	        user.setEnabled(true);
	        user.setImageUrl("default.png");
	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        User result = this.userRepository.save(user);
	        
	        
            session.removeAttribute("userRegistrationMessage");
	        model.addAttribute("session", session);
	        model.addAttribute("user", new User());
	        session.setAttribute("userRegistrationMessage", new Message("Successfully registered!!!", "alert-success"));
	        return "signup";
	    } catch (Exception e) {
	        e.printStackTrace();
	        model.addAttribute("user", user);
	        model.addAttribute("session", session);
	        session.setAttribute("message", new Message("Something Went Wrong " + e.getMessage(), "alert-danger"));
	        return "signup";
	    }
	}

	@GetMapping("/signin")
	public String customLogin(Model model) {
		
		model.addAttribute("title","Login Page");
		return "login";
	}
	
	@RequestMapping("/PetSitterSignUp")
    public String PetSitterSignup(Model model) {
        model.addAttribute("title", "Register as Pet Sitter - Pet Shelter Management");
        model.addAttribute("petSitter", new PetSitter());
        return "petSitterSignUp";
    }

    @PostMapping("/Pet_Sitter_do_register")
    public String registerPetSitter(@Valid @ModelAttribute("petSitter") PetSitter petSitter,
            BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
            Model model, HttpSession session) {
        try {
            if (!agreement) {
                throw new Exception("You have not agreed to the terms and conditions.");
            }

            if (result.hasErrors()) {
                model.addAttribute("petSitter", petSitter);
                return "petSitterSignUp";
            }
            
            petSitter.setRole("ROLE_PETSITTER");
            petSitter.setEnabled(true);
            petSitter.setImageUrl("default.png");

            // Encode the password
            petSitter.setPassword(passwordEncoder.encode(petSitter.getPassword()));

            // Additional processing or validation can be added here.

            PetSitter resultSitter = this.petSitterRepository.save(petSitter);

            session.removeAttribute("petSitterRegistrationMessage");
            model.addAttribute("session", session);
            model.addAttribute("petSitter", new PetSitter());
            session.setAttribute("petSitterRegistrationMessage", new Message("Successfully registered as a Pet Sitter!", "alert-success"));
            return "petSitterSignUp";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("petSitter", petSitter);
            model.addAttribute("session", session);
            session.setAttribute("message", new Message("Something Went Wrong: " + e.getMessage(), "alert-danger"));
            return "petSitterSignUp";
        }
    }
}
	
	
	

