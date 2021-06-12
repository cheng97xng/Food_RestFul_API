package com.JointITdev.FoodRestFullAPI.service;

import java.util.List;

import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameExistException;
import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameNotFoundException;
import com.JointITdev.FoodRestFullAPI.models.User;

public interface UserService {

	List<User> getAllUser();
	User add(User user) throws UsernameExistException, UsernameNotFoundException;
	User update(User user,Long id) throws UsernameNotFoundException, UsernameExistException;
	User findUsername(String username);
	void deleteUserId(Long id);
	
}
