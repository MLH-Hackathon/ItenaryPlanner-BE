package com.example.itenaryplanner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TripInfo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "from_place")
	private String fromPlace;

	@Column(name = "to_place")
	private String toPlace;

	@Column
	private String numOfTraveler;

	@Column(name = "from_date")
	private String fromDate;

	@Column(name = "to_date")
	private String toDate;

	@Column(name = "total_expense")
	private Float totalExpense;
}
