package com.example.itenaryplanner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.itenaryplanner.model.TripActivity;

@Repository
public interface TripActivityRepository extends JpaRepository<TripActivity, Long> {

	@Query("SELECT a FROM TripActivity a WHERE a.tripId = :tripId")
	List<TripActivity> findByTripId(@Param("tripId") Long id);

}
