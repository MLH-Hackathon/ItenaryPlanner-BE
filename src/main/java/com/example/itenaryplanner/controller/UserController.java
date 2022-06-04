package com.example.itenaryplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.dto.NewUserRequest;
import com.example.itenaryplanner.exception.DuplicateUsernameException;
import com.example.itenaryplanner.exception.PasswordMismatchException;
import com.example.itenaryplanner.exception.UploadFileException;
import com.example.itenaryplanner.response.AuthenticationRequest;
import com.example.itenaryplanner.response.AuthenticationResponse;
import com.example.itenaryplanner.security.CustomDetailsService;
import com.example.itenaryplanner.security.JwtUtil;
import com.example.itenaryplanner.service.UserService;

@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    CustomDetailsService customDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public String returnHello(){
        return "Hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        } catch (BadCredentialsException e){
            throw new Exception("Incorrect Username or Password", e);
        }

        UserDetails userDetails = customDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(value = "/signup", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> signup(@RequestPart NewUserRequest newUser, @RequestPart MultipartFile file) {
    	try {
    		String msg = userService.signup(newUser, file);
    		
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
