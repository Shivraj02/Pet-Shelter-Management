package com.smart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan("com.smart")
public class MyConfig {

	@Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private PetSitterDetailsServiceImpl petSitterDetailsServiceImpl;
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/pet_sitter/**").hasRole("PETSITTER")
                .requestMatchers("/**").permitAll())
                .formLogin(formLogin -> formLogin
                        .loginPage("/signin")
                        .loginProcessingUrl("/dologin")
                        .defaultSuccessUrl("/")
                        .successHandler((request, response, authentication) -> {
                            // Determine the target URL based on the user's role
                            String targetUrl = determineTargetUrl(authentication);
                            response.sendRedirect(response.encodeRedirectURL(targetUrl));
                        })
                    )
                .csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsServiceImpl)
                .userDetailsService(petSitterDetailsServiceImpl);
                

        
        return http.build();
    }
    
    private String determineTargetUrl(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PETSITTER"))) {
            return "/pet_sitter/index";
        }
        return "/user/index";
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    
    @Bean
    public UserDetailsService petSitterDetailsService() {
        return new PetSitterDetailsServiceImpl();
    }

    
    

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Bean
    public DaoAuthenticationProvider petSitterAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(petSitterDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }
    
    
    
}
