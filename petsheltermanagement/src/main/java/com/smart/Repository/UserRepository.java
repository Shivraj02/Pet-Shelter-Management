package com.smart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entites.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email")String email);
	
	@Query("SELECT u.name FROM User u WHERE u.id = :userId")
    String findUserNameById(@Param("userId") int userId);
}
