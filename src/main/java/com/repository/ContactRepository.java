package com.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Contact;
import com.entity.Customer;
import com.entity.Profession;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

	public List<Contact> findByCustomer(Customer customer);

	public boolean existsByProfession(Profession profession);
	
	@Query("SELECT ct FROM Contact ct WHERE :commercialId IN (SELECT c.id FROM ct.customer.commercials c)")
	public List<Contact> findByCommercial(@Param("commercialId") Integer commercialId);
	
	@Query("SELECT ct FROM Contact ct WHERE (SELECT COUNT(c.id) FROM ct.customer.commercials c WHERE c.id IN :commercialsId) > 0")
	public List<Contact> findByCommercials(@Param("commercialsId") Set<Integer> commercialsId);

	@Query("SELECT ct FROM Contact ct where (ct.name like %:search% and ct.profession.name like %:search%)")
	public Page<Contact> findAllAndNameContaining(Pageable pageable,@Param("search") String search);
 
}
