package com.example.itenaryplanner.dto;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TripActivityRequest {

	private MultipartFile pic;
	private String title;
	private String wentOn;
	private Float amtSpent;

	@Size(max = 2000, message = "Maximum 2000 characters allowed")
	private String desc;

	private String address;
}
