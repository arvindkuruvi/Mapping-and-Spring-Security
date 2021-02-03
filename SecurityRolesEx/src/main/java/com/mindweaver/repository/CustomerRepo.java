package com.mindweaver.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindweaver.domain.Customer;
import com.mindweaver.domain.Role;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{
	
	Customer findByEmail(String email);
	
	boolean existsByPhone(String phone);
	
	boolean existsByEmail(String email);

	Optional<Customer> findByPhone(String uid);
	
//	Customer findByPhone(String uid);
	
	@Query(value = "SELECT * FROM customer a inner join customer_roles b on a.id = b.cust_id WHERE b.role_id = :roleId", nativeQuery = true)
	Set<Role> findAllRoleByUserId(@Param("roleId") Long roleId);

}
