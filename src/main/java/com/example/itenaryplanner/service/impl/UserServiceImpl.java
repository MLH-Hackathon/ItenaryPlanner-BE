package com.example.itenaryplanner.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.dao.UserRepository;
import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.exception.DuplicateUsernameException;
import com.example.itenaryplanner.exception.PasswordMismatchException;
import com.example.itenaryplanner.model.User;
import com.example.itenaryplanner.response.AuthenticationRequest;
import com.example.itenaryplanner.security.ApplicationUser;
import com.example.itenaryplanner.security.JwtUtil;
import com.example.itenaryplanner.service.UserService;
import com.example.itenaryplanner.util.FileUtils;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
//	private AmazonClient awsClient;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, @Qualifier("bcrypt") PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
//		this.awsClient = awsClient;
	}

	@Override
	public String signup(NewUserRequest newUser) throws RuntimeException {
		User user = userRepository.findByUsername(newUser.getEmail()).orElse(null);

		if (user != null) {
			throw new DuplicateUsernameException("Username already exists");
		}

		final String encodedPwd = passwordEncoder.encode(newUser.getPassword());
		if (!passwordEncoder.matches(newUser.getCnfPassword(), encodedPwd)) {
			throw new PasswordMismatchException("Password mismatch");
		}

		user = new User();
		user.setFullName(newUser.getName());
		user.setPassword(encodedPwd);
		user.setUsername(newUser.getEmail());

		/*
		 * FileUtils.validateFile(file);
		 * 
		 * try { user.setProfilePicture(file.getBytes().toString()); } catch
		 * (IOException e) { log.error("Error while uploading profile picture"); }
		 */

		user = userRepository.save(user);

		return "SignUp Successful";
	}

	@Override
	public String authenticate(AuthenticationRequest authenticationRequest) {
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		UserDetails user = (UserDetails) auth.getPrincipal();

		User appuser = userRepository.findByUsername(user.getUsername()).orElse(null);

		if (!passwordEncoder.matches(authenticationRequest.getPassword(), appuser.getPassword())) {
			throw new UsernameNotFoundException("Username/Password Incorrect");
		}

		return jwtUtil.generateToken(user);
	}

	@Override
	public String updateProfilePic(MultipartFile profilePic, ApplicationUser appUser) throws IOException {
		FileUtils.validateProfilePic(profilePic);

		User user = userRepository.findByUsername(appUser.getUsername()).orElse(null);

//		final String uploadUrl = awsClient.uploadImage(profilePic, true);
		user.setProfilePicture(null);
		userRepository.save(user);

		return "Profile updated.";
	}

	@Override
	public String getProfilePic(ApplicationUser appUser) {
		User user = userRepository.findByUsername(appUser.getUsername()).orElse(null);

		return user.getProfilePicture();
	}

}
