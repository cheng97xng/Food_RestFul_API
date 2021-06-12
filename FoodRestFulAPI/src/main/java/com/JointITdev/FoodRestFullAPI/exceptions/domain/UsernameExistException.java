package com.JointITdev.FoodRestFullAPI.exceptions.domain;

public class UsernameExistException extends RuntimeException{

	public UsernameExistException(String message) {
		super(message);
	}

}
