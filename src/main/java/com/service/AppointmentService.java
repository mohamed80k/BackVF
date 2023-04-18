package com.service;

import java.util.Date;
import java.util.List;

import com.dto.AppointmentAddDto;
import com.dto.AppointmentInfoDto;
import com.dto.AppointmentUpdateDto;

public interface AppointmentService {

	public AppointmentInfoDto add(AppointmentAddDto appointmentAdd);

	public AppointmentInfoDto update(AppointmentUpdateDto appointmentUpdate);

	public boolean delete(Integer id);

	public AppointmentInfoDto getOne(Integer id);

	public List<AppointmentInfoDto> getList();

	public List<AppointmentInfoDto> getListByDate(Date date);

	public List<AppointmentInfoDto> getListByDateAndCommercial(Date date, Integer commercialId);
}
