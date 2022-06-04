package com.example.itenaryplanner.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewUserRequest implements Serializable {

	private static final long serialVersionUID = 7927560601930281592L;

	private String name;
	private String email;
	private String password;
	private String cnfPassword;
}
