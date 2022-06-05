package com.example.itenaryplanner.service;

import com.example.itenaryplanner.dto.TripInfoRequest;
import com.example.itenaryplanner.security.ApplicationUser;

public interface TripInfoService {

	String addTripInfo(TripInfoRequest tripInfo, ApplicationUser userDetails);
}
