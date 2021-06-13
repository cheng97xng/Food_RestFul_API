package com.JointITdev.FoodRestFullAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.JointITdev.FoodRestFullAPI.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query(value="SELECT * FROM user WHERE username=:username",nativeQuery = true)
	User findUsername(@Param("username")String username);
}
