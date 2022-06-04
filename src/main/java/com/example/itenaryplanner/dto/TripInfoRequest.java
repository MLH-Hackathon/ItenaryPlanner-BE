package com.example.itenaryplanner.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripInfoRequest implements Serializable {

	private static final long serialVersionUID = -885771205188831338L;

	@NotNull
	private String from;

	@NotNull
	private String to;
	private String numOfTraveler = "1";

	@NotNull
	private String fromDate;

	@NotNull
	private String toDate;

	@NotNull
	private Float totalExpense;

	private List<TripActivityRequest> activities;
}
