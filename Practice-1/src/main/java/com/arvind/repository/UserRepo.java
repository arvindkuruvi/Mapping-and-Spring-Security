package com.arvind.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arvind.model.UserInfo;


@Repository
public interface UserRepo extends JpaRepository<UserInfo, Long>
{
	UserInfo findByEmail(String email);
	
	UserInfo findById(long uid);
	
	UserInfo findByPhone(String phone);
	
	boolean existsByPhone(String phoneNumber);
	
	boolean existsByEmail(String email);
}
