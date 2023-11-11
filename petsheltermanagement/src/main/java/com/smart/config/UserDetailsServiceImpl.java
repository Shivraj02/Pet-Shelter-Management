package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.dao.UserRepository;
import com.smart.entites.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.getUserByUserName(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found user");
			
		}
		
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}

	    public String getUserRole(String username) {
        // Implement logic to fetch and return the user's role based on the username
        // Example:
        User user = userRepository.getUserByUserName(username);
        if (user != null) {
            String role = user.getRole(); // Assuming there is a 'role' field in the User entity
            return (role != null) ? role : "USER"; // Provide a default role if 'role' is null
        }
        return "USER"; // Handle the case where the user is not found
    }
	

}
