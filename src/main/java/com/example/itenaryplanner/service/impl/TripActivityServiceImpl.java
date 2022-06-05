package com.example.itenaryplanner.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.itenaryplanner.dao.TripActivityRepository;
import com.example.itenaryplanner.dto.TripActivityRequest;
import com.example.itenaryplanner.model.TripActivity;
import com.example.itenaryplanner.model.TripInfo;
import com.example.itenaryplanner.service.TripActivityService;

@Service
public class TripActivityServiceImpl implements TripActivityService {

	private TripActivityRepository activityRepository;

	@Autowired
	public TripActivityServiceImpl(TripActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	@Override
	@Transactional
	public void addActivity(TripInfo trip, List<TripActivityRequest> activities) throws IOException {
		if (activities == null || activities.isEmpty()) {
			return;
		}

		List<TripActivity> tripActivities = new ArrayList<>();

		for (TripActivityRequest r : activities) {
			TripActivity activity = new TripActivity();
			activity.setTitle(r.getTitle());
			activity.setDesc(r.getDesc());
			activity.setWentOn(r.getWentOn());
			activity.setAmtSpent(r.getAmtSpent());
			activity.setTripId(trip.getId());
			activity.setAddress(r.getAddress());

			tripActivities.add(activity);
		}

		activityRepository.saveAll(tripActivities);
	}

}
