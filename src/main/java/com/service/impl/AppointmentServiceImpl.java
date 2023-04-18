package com.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.AppointmentAddDto;
import com.dto.AppointmentInfoDto;
import com.dto.AppointmentUpdateDto;
import com.entity.Appointment;
import com.entity.Commercial;
import com.entity.Tiers;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.AppointmentRepository;
import com.repository.CommercialRepository;
import com.repository.TiersRepository;
import com.service.AppointmentService;
import com.util.Utils;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private TiersRepository<Tiers> tiersRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public AppointmentInfoDto add(AppointmentAddDto appointmentAdd) {

		/**
		 * Vérification des dates
		 */
		Date date = new Date();

		if (appointmentAdd.getDate().compareTo(date) == -1) {
			throw new ResourceConflictException("Date de rendez-vous incorrect !");
		}

		/**
		 * Vérification de tiers du rendez-vous
		 */
		List<String> typesTiers = Arrays.asList("Client", "Prospect");
		Tiers tiers = tiersRepository.findByIdAndTypes(appointmentAdd.getTiers(), typesTiers);
		if (tiers == null)
			throw new ResourceNotFoundException("Client/Prospect non trouvé !");

		/**
		 * Vérification de commercial du rendez-vous
		 */
		if(!commercialRepository.existsById(appointmentAdd.getCommercial())) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}
		
		Commercial commercial = commercialRepository.getOne(appointmentAdd.getCommercial());
 
		/**
		 * Ajouter
		 */
		Appointment appointment = modelMapper.map(appointmentAdd, Appointment.class);

		appointment.setId(null);
		appointment.setTiers(tiers);
		appointment.setCommercial(commercial);

		appointmentRepository.save(appointment);

		/**
		 * Retourne data
		 */
		return modelMapper.map(appointment, AppointmentInfoDto.class);
	}

	@Override
	@Transactional
	public AppointmentInfoDto update(AppointmentUpdateDto appointmentUpdate) {

		/**
		 * Vérification de rendez-vous
		 */
		if (!appointmentRepository.existsById(appointmentUpdate.getId()))
			throw new ResourceNotFoundException("Rendez-vous non trouvé !");

		/**
		 * Vérification des dates
		 */
		Date date = new Date();

		if (appointmentUpdate.getDate().compareTo(date) == -1) {
			throw new ResourceConflictException("Date de rendez-vous incorrect !");
		}

		/**
		 * Vérification de client du rendez-vous
		 */
		List<String> typesTiers = Arrays.asList("Client", "Prospect");
		Tiers tiers = tiersRepository.findByIdAndTypes(appointmentUpdate.getTiers(), typesTiers);
		if (tiers == null)
			throw new ResourceNotFoundException("Client/Prospect non trouvé !");

		/**
		 * Vérification de commercial du rendez-vous
		 */
		if(!commercialRepository.existsById(appointmentUpdate.getCommercial())) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}
		
		Commercial commercial = commercialRepository.getOne(appointmentUpdate.getCommercial());
 
		/**
		 * Ajouter
		 */
		Appointment appointment = modelMapper.map(appointmentUpdate, Appointment.class);

		appointment.setTiers(tiers);
		appointment.setCommercial(commercial);

		appointmentRepository.save(appointment);

		/**
		 * Retourne data
		 */
		return modelMapper.map(appointment, AppointmentInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de rendez-vous
		 */
		if (!appointmentRepository.existsById(id))
			throw new ResourceNotFoundException("Rendez-vous non trouvé !");

		try {
			/**
			 * Supprimer
			 */
			appointmentRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public AppointmentInfoDto getOne(Integer id) {
		/**
		 * Vérification de rendez-vous
		 */
		
		if (!appointmentRepository.existsById(id)) {
			throw new ResourceNotFoundException("Rendez-vous non trouvé !");
		}

		Appointment appointment = appointmentRepository.getOne(id);
		
		return modelMapper.map(appointment, AppointmentInfoDto.class);
	}

	@Override
	public List<AppointmentInfoDto> getList() {
		return Utils.map(modelMapper, appointmentRepository.findAll(), AppointmentInfoDto.class);
	}

	@Override
	public List<AppointmentInfoDto> getListByDate(Date date) {
		return Utils.map(modelMapper, appointmentRepository.findByDate(date), AppointmentInfoDto.class);
	}

	@Override
	public List<AppointmentInfoDto> getListByDateAndCommercial(Date date, Integer commercialId) {
		/**
		 * Vérification de commercial
		 */
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, appointmentRepository.findByDateAndCommercial(date, commercialId),
				AppointmentInfoDto.class);
	}

}
