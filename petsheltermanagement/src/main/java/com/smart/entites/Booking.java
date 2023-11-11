package com.smart.entites;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    
    
    
    @ManyToOne
    @JoinColumn(name = "pet_sitter_id")
    private PetSitter petSitter;
    
    

	

	@ManyToOne
    private User user;  
    
    @ManyToOne
    private Pet pet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	
	public Booking(Long id, LocalDateTime startTime, LocalDateTime endTime, String status, PetSitter petSitter,
			User user, Pet pet, String petSitterName, int petSitterId, Long petSitterId2) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.petSitter = petSitter;
		
		
		this.user = user;
		this.pet = pet;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", status=" + status
				+ ", petSitter=" + petSitter + ", user=" + user + ", pet=" + pet + "]";
	}

	public PetSitter getPetSitter() {
		return petSitter;
	}

	public void setPetSitter(PetSitter petSitter) {
		this.petSitter = petSitter;
	}

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	

	
	
    
    
}

