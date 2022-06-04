package com.example.itenaryplanner.service;

import javax.security.auth.login.CredentialException;

import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.response.AuthenticationRequest;

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
}
