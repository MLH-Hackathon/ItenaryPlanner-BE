package com.example.itenaryplanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.itenaryplanner.dto.TripInfoRequest;
import com.example.itenaryplanner.security.ApplicationUser;
import com.example.itenaryplanner.service.TripInfoService;

@RequestMapping("/trip")
@RestController
public class TripInfoController {

	private TripInfoService tripInfoService;

	@Autowired
	public TripInfoController(TripInfoService tripInfoService) {
		this.tripInfoService = tripInfoService;
	}

	@PostMapping
	public ResponseEntity<String> addTripDetails(@RequestBody TripInfoRequest tripInfo,
			@RequestAttribute("user") ApplicationUser userDetails) {
		String msg = tripInfoService.addTripInfo(tripInfo, userDetails);
		
		return ResponseEntity.ok(msg);
	}
}
