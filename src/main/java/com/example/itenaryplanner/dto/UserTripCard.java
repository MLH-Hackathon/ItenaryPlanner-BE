package com.example.itenaryplanner.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserTripCard {

	private Long id;
	private String name;

	private List<TripDetailsDto> trip;
}
