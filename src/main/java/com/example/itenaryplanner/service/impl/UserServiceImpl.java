package com.example.itenaryplanner.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.dao.UserRepository;
import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.exception.DuplicateUsernameException;
import com.example.itenaryplanner.exception.PasswordMismatchException;
import com.example.itenaryplanner.model.User;
import com.example.itenaryplanner.service.UserService;
import com.example.itenaryplanner.util.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String signup(NewUserRequest newUser, MultipartFile file) throws RuntimeException {
		User user = userRepository.findByUsername(newUser.getEmail()).orElse(null);

		if (user != null) {
			throw new DuplicateUsernameException("Username already exists");
		}

		if (!passwordEncoder.matches(newUser.getCnfPassword(), passwordEncoder.encode(newUser.getPassword()))) {
			throw new PasswordMismatchException("Password mismatch");
		}

		user = new User();
		user.setFullName(newUser.getName());
		user.setPassword(newUser.getPassword());
		user.setUsername(newUser.getEmail());

		FileUtils.validateFile(file);

		try {
			user.setProfilePicture(file.getBytes().toString());
		} catch (IOException e) {
			log.error("Error while uploading profile picture");
		}

		user = userRepository.save(user);

		return "SignUp Successful";
	}

}
