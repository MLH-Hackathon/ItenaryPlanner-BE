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
public class TripActivity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

	@Column(name = "trip_id")
	private Long tripId;

	@Column(name = "memorable_pic")
	private String pic;

	private String title;

	@Column(name = "went_on")
	private String wentOn;

	@Column(name = "amt_spent")
	private Float amtSpent;

	@Column(name = "description")
	private String desc;
}
