package com.example.itenaryplanner.service;

import java.io.IOException;

import javax.security.auth.login.CredentialException;

import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.response.AuthenticationRequest;
import com.example.itenaryplanner.security.ApplicationUser;

public interface UserService {

	/**
	 * Service to allow user to sign up.
	 * 
	 * @param newUser
	 * @return 
	 * @throws CredentialException 
	 */
	String signup(NewUserRequest newUser) throws RuntimeException;

	/**
	 * Service to authenticate user and generate token if user is valid
	 * 
	 * @param authenticationRequest
	 * @return token
	 */
	String authenticate(AuthenticationRequest authenticationRequest);

	/**
	 * Service to update profile picture of user
	 * 
	 * @param profilePic
	 * @param user
	 * @return
	 * @throws IOException 
	 */
	String updateProfilePic(MultipartFile profilePic, ApplicationUser user) throws IOException;

	/**
	 * Get profile picture
	 * 
	 * @param user
	 * @return path
	 */
	String getProfilePic(ApplicationUser user);
}
