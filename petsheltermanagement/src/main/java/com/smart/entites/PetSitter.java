package com.smart.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PetSitter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String experience;
    private String password;
    private double rates;
    private String role;
	private boolean enabled;
	private String imageUrl;
    
    private String contactDetails;

    @ManyToOne
    private User user;
    
    @ManyToOne
    private Pet pet;
    
    @ManyToOne
    private Booking booking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getRates() {
		return rates;
	}

	public void setRates(double rates) {
		this.rates = rates;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@Override
	public String toString() {
		return "PetSitter [id=" + id + ", name=" + name + ", email=" + email + ", experience=" + experience
				+ ", password=" + password + ", rates=" + rates + ", role=" + role + ", enabled=" + enabled
				+ ", imageUrl=" + imageUrl + ", contactDetails=" + contactDetails + ", user=" + user + ", pet=" + pet
				+ ", booking=" + booking + "]";
	}

	public PetSitter(Long id, String name, String email, String experience, String password, double rates, String role,
			boolean enabled, String imageUrl, String contactDetails, User user, Pet pet, Booking booking) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.experience = experience;
		this.password = password;
		this.rates = rates;
		this.role = role;
		this.enabled = enabled;
		this.imageUrl = imageUrl;
		this.contactDetails = contactDetails;
		this.user = user;
		this.pet = pet;
		this.booking = booking;
	}

	public PetSitter() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
    
	
	
    
    
}
