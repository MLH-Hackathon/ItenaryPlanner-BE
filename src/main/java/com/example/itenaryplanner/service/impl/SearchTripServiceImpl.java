package com.example.itenaryplanner.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.itenaryplanner.dao.TripActivityRepository;
import com.example.itenaryplanner.dao.TripInfoRepository;
import com.example.itenaryplanner.dao.UserRepository;
import com.example.itenaryplanner.dto.ActivityDto;
import com.example.itenaryplanner.dto.TripDetailsDto;
import com.example.itenaryplanner.dto.UserTripCard;
import com.example.itenaryplanner.model.TripActivity;
import com.example.itenaryplanner.model.TripInfo;
import com.example.itenaryplanner.model.User;
import com.example.itenaryplanner.service.SearchTripService;

@Service
public class SearchTripServiceImpl implements SearchTripService {

	private TripInfoRepository tripInfoRepository;
	private UserRepository userRepository;
	private TripActivityRepository activityRepository;
	
	private final Executor executor = Executors.newFixedThreadPool(5);

	@Autowired
	public SearchTripServiceImpl(
			TripInfoRepository tripInfoRepository,
			UserRepository userRepository,
			TripActivityRepository activityRepository) {
		this.tripInfoRepository = tripInfoRepository;
		this.userRepository = userRepository;
		this.activityRepository = activityRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserTripCard> search(String q, String sort) {
		List<TripInfo> trips;
		if (q == null || q.isBlank()) {
			trips = tripInfoRepository.findAll();
		} else {
			trips = tripInfoRepository.performWildCardSearch(q.toLowerCase());
		}

		if (trips == null || trips.isEmpty()) {
			return Collections.emptyList();
		}

		ConcurrentHashMap<Long, User> userCache = new ConcurrentHashMap<>();

		List<UserTripCard> userInfo = new ArrayList<>();
		List<CompletableFuture<Boolean>> completableList = new ArrayList<>();
		trips.stream().forEach(trip -> {
			UserTripCard tripCard = new UserTripCard();
			tripCard.setTrip(new ArrayList<>());
			userInfo.add(tripCard);

			completableList.add(populateTripDetails(tripCard, trip, userCache));
		});

		completableList.forEach(c -> c.join());

		return userInfo;
	}

	private CompletableFuture<Boolean> populateTripDetails(UserTripCard tripCard, TripInfo trip, ConcurrentHashMap<Long, User> userCache) {
		return CompletableFuture.supplyAsync(() -> {
			TripDetailsDto details = new TripDetailsDto();
			details.setId(trip.getId().toString());
			details.setFromDate(trip.getFromDate());
			details.setToDate(trip.getToDate());
			details.setFromPlace(trip.getFromPlace());
			details.setToPlace(trip.getToPlace());
			details.setNumberOfPeople(trip.getNumOfTraveler());
			details.setTotalCost(trip.getTotalExpense().toString());
			tripCard.getTrip().add(details);

			if (!userCache.containsKey(trip.getUserId())) {
				User user = userRepository.findById(trip.getUserId()).orElse(null);

				if (user != null) {
					userCache.put(trip.getUserId(), user);
				}
			}

			if (userCache.containsKey(trip.getUserId())) {
				User user = userCache.get(trip.getUserId());

				tripCard.setId(user.getId());
				tripCard.setName(user.getFullName());

				List<TripActivity> activities = activityRepository.findByTripId(trip.getId());
				details.setActivity(new ArrayList<>());
				if (activities != null && !activities.isEmpty()) {
					for (TripActivity activity : activities) {
						ActivityDto dto = new ActivityDto();
						dto.setId(activity.getId().toString());
						dto.setName(activity.getTitle());
						dto.setDescription(activity.getDesc());
						dto.setAddress(activity.getAddress());
						dto.setCost(activity.getAmtSpent().toString());

						details.getActivity().add(dto);
					}
				}
			}

			return true;
		}, executor);		
	}
}
