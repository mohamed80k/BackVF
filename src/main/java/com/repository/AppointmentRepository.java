package com.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	@Query("SELECT a FROM Appointment a WHERE DATE(a.date) = DATE(:date)")
	public List<Appointment> findByDate(@Param("date") Date date);
	
	@Query("SELECT a FROM Appointment a WHERE DATE(a.date) = DATE(:date) AND a.commercial.id = :commercialId")
	public List<Appointment> findByDateAndCommercial(@Param("date") Date date, @Param("commercialId") Integer commercialId);
}
