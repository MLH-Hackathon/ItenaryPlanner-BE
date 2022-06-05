package com.example.itenaryplanner.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.itenaryplanner.dao.UserRepository;

@Service
public class CustomDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		com.example.itenaryplanner.model.User appUser = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Username/Password Incorrect"));
    	
        return new ApplicationUser(appUser);
    }
}
