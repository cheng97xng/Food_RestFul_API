package com.JointITdev.FoodRestFullAPI.exceptions.domain;

public class UsernameNotFoundException extends RuntimeException{

	public UsernameNotFoundException(String message) {
		super(message);
	}

}
