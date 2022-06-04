package com.example.itenaryplanner.exception;

public class DuplicateUsernameException extends RuntimeException {

	private static final long serialVersionUID = -6040778418136427012L;

	public DuplicateUsernameException(String msg) {
		super(msg);
	}
}