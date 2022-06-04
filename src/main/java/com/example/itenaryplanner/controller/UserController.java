package com.example.itenaryplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.exception.DuplicateUsernameException;
import com.example.itenaryplanner.exception.PasswordMismatchException;
import com.example.itenaryplanner.exception.UploadFileException;
import com.example.itenaryplanner.response.AuthenticationRequest;
import com.example.itenaryplanner.response.AuthenticationResponse;
import com.example.itenaryplanner.security.CustomDetailsService;
import com.example.itenaryplanner.service.UserService;

@RestController
public class UserController {

	@Autowired
    CustomDetailsService customDetailsService;

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public String returnHello(){
        return "Hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
    	
    	try {
	    	final String token = userService.authenticate(authenticationRequest);
	    	
	    	return ResponseEntity.ok(new AuthenticationResponse(token));
    	} catch (BadCredentialsException e) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    	} catch (Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody NewUserRequest newUser) {
    	try {
    		String msg = userService.signup(newUser);
    		
    		return ResponseEntity.ok(msg);
    	} catch (PasswordMismatchException | UploadFileException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	} catch (DuplicateUsernameException e) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    	} catch (Exception e) {
    		return ResponseEntity.internalServerError().build();
    	}
    }
}
