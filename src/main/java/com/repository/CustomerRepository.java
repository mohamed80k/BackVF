package com.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Customer;
import com.entity.TypeCustomer;

public interface CustomerRepository extends TiersRepository<Customer> {

	public boolean existsByTypeCustomer(TypeCustomer typeCustomer);
	
	@Query("SELECT c FROM Customer c WHERE c.id IN :customersId")
	public Set<Customer> findByCustomersId(@Param("customersId") Set<Integer> customersId);
	
	@Query("SELECT c FROM Customer c WHERE c.typeCustomer.name = 'Revendeur' ")
	public Set<Customer> findAllRevendeurs();
	
	@Query("SELECT c FROM Customer c WHERE c.createAt BETWEEN :startDate AND :endDate")
	public List<Customer> finAllByCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("SELECT cr FROM Customer cr WHERE (SELECT COUNT(c.id) FROM cr.commercials c WHERE c.id IN :commercialsId) > 0 ORDER BY cr.createAt DESC")
	public List<Customer> findCustomersByCommercials(@Param("commercialsId") Set<Integer> commercialsId);
}
