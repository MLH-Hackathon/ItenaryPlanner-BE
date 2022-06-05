package com.example.itenaryplanner.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.itenaryplanner.dao.TripInfoRepository;
import com.example.itenaryplanner.dto.TripInfoRequest;
import com.example.itenaryplanner.model.TripInfo;
import com.example.itenaryplanner.model.User;
import com.example.itenaryplanner.security.ApplicationUser;
import com.example.itenaryplanner.service.TripActivityService;
import com.example.itenaryplanner.service.TripInfoService;

@Service
public class TripInfoServiceImpl implements TripInfoService {

	private TripInfoRepository tripInfoRepository;
	private TripActivityService activityService;

	@Autowired
	public TripInfoServiceImpl(TripInfoRepository tripInfoRepository, TripActivityService activityService) {
		this.tripInfoRepository = tripInfoRepository;
		this.activityService = activityService;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String addTripInfo(TripInfoRequest tripInfo, ApplicationUser userDetails) {
		TripInfo trip = new TripInfo();
		trip.setFromPlace(tripInfo.getFrom());
		trip.setToPlace(tripInfo.getTo());
		trip.setNumOfTraveler(trip.getNumOfTraveler());
		trip.setFromDate(tripInfo.getFromDate());
		trip.setToDate(tripInfo.getToDate());
		trip.setTotalExpense(tripInfo.getTotalExpense());

		User user = userDetails.getUser();
		trip.setUserId(user.getId());
		
		trip = tripInfoRepository.save(trip);

		try {
			activityService.addActivity(trip, tripInfo.getActivities());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return "Trip details saved";
	}
}
