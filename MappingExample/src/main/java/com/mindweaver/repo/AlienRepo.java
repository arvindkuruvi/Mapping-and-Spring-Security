package com.mindweaver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindweaver.domain.Alien;

@Repository
public interface AlienRepo extends JpaRepository<Alien, Integer>{
	
	

}
