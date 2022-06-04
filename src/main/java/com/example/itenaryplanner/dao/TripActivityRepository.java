package com.example.itenaryplanner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.itenaryplanner.model.TripActivity;

@Repository
public interface TripActivityRepository extends JpaRepository<TripActivity, Long> {

}
