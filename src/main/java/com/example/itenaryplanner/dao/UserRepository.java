package com.example.itenaryplanner.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.itenaryplanner.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long > {

	@Query("SELECT u FROM User u WHERE lower(u.username) = lower(:username)")
	Optional<User> findByUsername(@Param("username") String username);


}
