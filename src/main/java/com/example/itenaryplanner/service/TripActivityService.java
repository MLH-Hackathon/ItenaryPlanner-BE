package com.example.itenaryplanner.service;

import java.io.IOException;
import java.util.List;

import com.example.itenaryplanner.dto.TripActivityRequest;
import com.example.itenaryplanner.model.TripInfo;

public interface TripActivityService {

	/**
	 * Service to add activities done on a given trip
	 * @param trip
	 * @param activities
	 * @throws IOException 
	 */
	void addActivity(TripInfo trip, List<TripActivityRequest> activities) throws IOException;
}
