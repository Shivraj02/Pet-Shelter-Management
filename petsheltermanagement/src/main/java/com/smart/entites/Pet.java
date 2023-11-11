package com.smart.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;

@Entity
public class Pet {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
    @Size(min = 1, max = 255)
    private String name;

	
    @Min(value = 0, message = "Age must be a non-negative number")
    private int age;

    @NotBlank(message = "Breed is required")
    @Size(min = 1, max = 255, message = "Breed length must be between 1 and 255 characters")
    private String breed;

    @NotBlank(message = "Gender is required")
    @Size(min = 1, max = 255)
    private String gender;

    

	@Size(max = 255, message = "Medical needs length must be at most 255 characters")
    private String medicalNeeds;
    
    @ManyToOne
	private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMedicalNeeds() {
		return medicalNeeds;
	}

	public void setMedicalNeeds(String medicalNeeds) {
		this.medicalNeeds = medicalNeeds;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", age=" + age + ", breed=" + breed + ", gender=" + gender
				+ ", medicalNeeds=" + medicalNeeds + "]";
	}

	public Pet(Long id, String name, int age, String breed, String gender, String medicalNeeds) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.breed = breed;
		this.gender = gender;
		this.medicalNeeds = medicalNeeds;
	}

	public Pet() {
		super();
		// TODO Auto-generated constructor stub
	}

}
