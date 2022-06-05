package com.example.itenaryplanner.service;

import java.util.List;

import com.example.itenaryplanner.dto.UserTripCard;

public interface SearchTripService {

	/**
	 * Perform search on trip based on query. Data will be sorted based on sort option provided.
	 * 
	 * @param q
	 * @param sort
	 * @return 
	 */
	List<UserTripCard> search(final String q, final String sort);
}
