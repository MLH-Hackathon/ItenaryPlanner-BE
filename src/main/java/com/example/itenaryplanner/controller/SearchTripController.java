package com.example.itenaryplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.itenaryplanner.dto.UserTripCard;
import com.example.itenaryplanner.service.SearchTripService;

@RequestMapping("/search")
@RestController
public class SearchTripController {

	private SearchTripService searchTripService;

	@Autowired
	public SearchTripController(SearchTripService searchTripService) {
		this.searchTripService = searchTripService;
	}

	@GetMapping(params = { "q", "sort" })
	public ResponseEntity<List<UserTripCard>> search(@RequestParam(name = "q", required = false) String q, @RequestParam(name = "sort", required = false) String sort) {
		List<UserTripCard> trips = searchTripService.search(q, sort);
		if (trips != null && !trips.isEmpty()) {
			return ResponseEntity.ok(trips);
		}

		return ResponseEntity.noContent().build();
	}
}
