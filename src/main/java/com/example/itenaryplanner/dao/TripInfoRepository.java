package com.example.itenaryplanner.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.itenaryplanner.model.TripInfo;

@Repository
public interface TripInfoRepository extends JpaRepository<TripInfo, Long> {

	@Query("SELECT t FROM TripInfo t WHERE lower(t.fromPlace) = :q OR lower(t.toPlace) = :q")
	List<TripInfo> performWildCardSearch(@Param("q") String q);
}
