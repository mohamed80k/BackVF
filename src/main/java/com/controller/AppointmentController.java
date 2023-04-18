package com.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AppointmentAddDto;
import com.dto.AppointmentInfoDto;
import com.dto.AppointmentUpdateDto;
import com.service.AppointmentService;

@RestController
@RequestMapping(value = "/api/appointments")
@CrossOrigin
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public AppointmentInfoDto getOne(@PathVariable("id") Integer id) {
		return appointmentService.getOne(id);
	}

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public List<AppointmentInfoDto> getList() {
		return appointmentService.getList();
	}

	@RequestMapping(value = "/list-by-date", method = RequestMethod.GET)
	public List<AppointmentInfoDto> getListByDate(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
		return appointmentService.getListByDate(date);
	}

	@RequestMapping(value = "/list-by-date-and-commercial", method = RequestMethod.GET)
	public List<AppointmentInfoDto> getListByDateAndCommercial(
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
			@RequestParam("commercialId") Integer commercialId) {
		return appointmentService.getListByDateAndCommercial(date, commercialId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public AppointmentInfoDto add(@Valid @RequestBody AppointmentAddDto appointmentAdd) {
		return appointmentService.add(appointmentAdd);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public AppointmentInfoDto update(@Valid @RequestBody AppointmentUpdateDto appointmentUpdate) {
		return appointmentService.update(appointmentUpdate);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public boolean delete(@PathVariable("id") Integer id) {
		return appointmentService.delete(id);
	}

}
