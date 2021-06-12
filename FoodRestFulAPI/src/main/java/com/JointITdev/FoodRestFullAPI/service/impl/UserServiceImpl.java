package com.JointITdev.FoodRestFullAPI.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameExistException;
import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameNotFoundException;
import com.JointITdev.FoodRestFullAPI.models.User;
import com.JointITdev.FoodRestFullAPI.repository.UserRepository;
import com.JointITdev.FoodRestFullAPI.service.UserService;

import net.bytebuddy.implementation.bytecode.Throw;

//import com.dev.APIRestFulsupport.exception.domain.HighlightIdExistException;
//import com.dev.APIRestFulsupport.exception.domain.HighlightIdNotFoundException;
//import com.dev.APIRestFulsupport.models.Highlight;
@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepo;
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public User add(User user) throws UsernameExistException, UsernameNotFoundException {
		
		User addUser = new User();
		try {
			if (!user.getUsername().isBlank() || !user.getName().isBlank() || !user.getPassword().isBlank()
					|| !user.getChooseType().isBlank()) {
				User validateUser=userRepo.findUsername(user.getUsername());
				if(validateUser!=null) {
					throw new UsernameExistException("User is already exist");
				}else {
					addUser.setName(user.getName());
					addUser.setUsername(user.getUsername());
					addUser.setPassword(user.getPassword());
					addUser.setChooseType(user.getChooseType());
					userRepo.save(addUser);
				}
				return addUser;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public User update(User user, Long id) throws UsernameNotFoundException, UsernameExistException {
		User updateUser =userRepo.findById(id).get();
		try {
//			if (!user.getUsername().isBlank() || !user.getName().isBlank() || !user.getPassword().isBlank()
//					|| !user.getChooseType().isBlank()) {
//				
//			}
			if(updateUser!=null) {
				updateUser.setName(user.getName());
				updateUser.setUsername(user.getUsername());
				updateUser.setPassword(user.getPassword());
				updateUser.setChooseType(user.getChooseType());
				userRepo.save(updateUser);
				return updateUser;
			}else {
				throw new UsernameNotFoundException("User was not found id: "+id);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return updateUser;
			
		}
	}

	@Override
	public User findUsername(String username) {
		return userRepo.findUsername(username);
	}

	@Override
	public void deleteUserId(Long id) {
		try {
			if (id != null||id>0) {
				userRepo.deleteById(id);
			}else {
				throw new UsernameNotFoundException("User was not found id: "+id);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	private User validateUsername(String username) throws UsernameExistException, UsernameNotFoundException {
		User user = userRepo.findUsername(username);
		if (StringUtils.isNotBlank(username)) {
//			if (user == null) {
//				throw new UsernameNotFoundException("User was not found username " + username);
//			}

			LOGGER.info("user id : " + user.getId());
			if (user != null) {
				throw new UsernameExistException("User is already exist");
			}
			return user;
		}

		return null;
	}

}
