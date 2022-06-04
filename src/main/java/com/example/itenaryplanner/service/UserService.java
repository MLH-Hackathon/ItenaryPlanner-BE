package com.example.itenaryplanner.service;

import javax.security.auth.login.CredentialException;

import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.dto.NewUserRequest;

public interface UserService {

	/**
	 * Service to allow user to sign up.
	 * 
	 * @param newUser
	 * @param file
	 * @return 
	 * @throws CredentialException 
	 */
	String signup(NewUserRequest newUser, MultipartFile file) throws RuntimeException;
}
