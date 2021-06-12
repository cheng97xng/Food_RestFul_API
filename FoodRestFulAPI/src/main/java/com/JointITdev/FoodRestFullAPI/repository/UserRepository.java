package com.JointITdev.FoodRestFullAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.JointITdev.FoodRestFullAPI.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value="SELECT * FROM user WHERE username=:username",nativeQuery = true)
	User findUsername(@Param("username")String username);
}
