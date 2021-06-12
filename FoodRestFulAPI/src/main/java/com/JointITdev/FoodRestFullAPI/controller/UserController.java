package com.JointITdev.FoodRestFullAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameExistException;
import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameNotFoundException;
import com.JointITdev.FoodRestFullAPI.models.User;
import com.JointITdev.FoodRestFullAPI.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUser(){
		List<User> user=userService.getAllUser();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping("/find/{username}")
	public ResponseEntity<User>findUser(@PathVariable("username")String username){
		User findUser=userService.findUsername(username);
		return new ResponseEntity<>(findUser,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<User> addUser(@RequestBody User user) throws UsernameExistException, UsernameNotFoundException{
		User adduser=userService.add(user);
		return new ResponseEntity<>(adduser,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable("id") Long id) throws UsernameNotFoundException, UsernameExistException{
		User updateUser=userService.update(user, id);
		return new ResponseEntity<>(updateUser,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id")Long id){
		userService.deleteUserId(id);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
