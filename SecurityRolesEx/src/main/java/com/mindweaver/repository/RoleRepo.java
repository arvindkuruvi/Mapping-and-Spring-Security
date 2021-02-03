package com.mindweaver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindweaver.domain.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

	Role findByRoleName(String role);
}
