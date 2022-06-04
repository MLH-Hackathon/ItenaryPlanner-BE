package com.example.itenaryplanner.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.itenaryplanner.model.TripInfo;

@Repository
public interface TripInfoRepository extends JpaRepository<TripInfo, Long> {

}
