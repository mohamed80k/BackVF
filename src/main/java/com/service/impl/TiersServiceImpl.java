package com.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.entity.*;
import com.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DateInterval;
import com.dto.PageDto;
import com.dto.TiersAddDto;
import com.dto.TiersInfoDto;
import com.dto.TiersUpdateDto;
import com.dto.report.StatisticInfoDto;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.service.CommercialService;
import com.service.TiersService;
import com.util.Utils;

@Service
public class TiersServiceImpl implements TiersService {

	@Autowired
	private TiersRepository<Tiers> tiersRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private TypeTiersRepository typetiersRepository;

	@Autowired
	private CommercialService commercialService;

	@Autowired
	EventTiersRepository eventTiersRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public TiersInfoDto add(TiersAddDto tiersAdd) {
		/** Vérification de tiers **/
		if (tiersRepository.existsByName(tiersAdd.getName()))
			throw new ResourceAlreadyExistException("Tiers existe déja !");

		/** Vérification des dates **/
		Date date = new Date();

		if (tiersAdd.getCreateAt() == null) {
			tiersAdd.setCreateAt(date);
		} else if (tiersAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du tiers incorrect !");
		}

		/** Vérification de type du tiers **/
		if (!typetiersRepository.existsById(tiersAdd.getTypeTiers())) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}

		TypeTiers typeTiers = typetiersRepository.getOne(tiersAdd.getTypeTiers());

		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();
		for (Integer commercialId : tiersAdd.getCommercials()) {
			if (!commercialRepository.existsById(commercialId)) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			} else {
				commercials.add(commercialRepository.getOne(commercialId));
			}
		}

		/** Ajouter **/
		Locality locality = modelMapper.map(tiersAdd.getLocality(), Locality.class);
		locality.setId(null);

		Tiers tiers = modelMapper.map(tiersAdd, Tiers.class);
		tiers.setId(null);
		tiers.setLocality(locality);
		tiers.setCommercials(commercials);
		tiers.setTypeTiers(typeTiers);

		tiersRepository.save(tiers);

		return modelMapper.map(tiers, TiersInfoDto.class);
	}

	@Override
	public boolean checkTiersInAffiliateCommercials(Tiers tiers) {
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();

		if (commercials == null) {
			return true;
		}

		for (Commercial commerial : commercials) {
			if (tiers.getCommercials().contains(commerial)) {
				return true;
			}
		}

		throw new ResourceNotFoundException(tiers.getTypeTiers().getName() + " non accessible !");
	}

	@Override
	@Transactional
	public TiersInfoDto update(TiersUpdateDto tiersUpdate) {

		/** Vérification de tiers **/
		Tiers tiersOld = tiersRepository.findById(tiersUpdate.getId()).orElse(null);

		if (tiersOld == null) {
			throw new ResourceNotFoundException("Tiers non trouvé !");
		}

		this.checkTiersInAffiliateCommercials(tiersOld);

		if (tiersOld.getTypeTiers().getName().equals("Client")) {
			/** Se détacher du projet **/
			for (Project project : tiersOld.getProjects()) {
				project.getTiers().remove(tiersOld);
			}
			customerRepository.deleteById(tiersUpdate.getId());
		}

		if (!tiersOld.getName().equals(tiersUpdate.getName()) && tiersRepository.existsByName(tiersUpdate.getName()))
			throw new ResourceAlreadyExistException("Tiers existe déja !");

		/** Vérification des dates */
		Date date = new Date();

		if (tiersUpdate.getCreateAt() == null) {
			tiersUpdate.setCreateAt(date);
		} else if (tiersUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du tiers incorrect !");
		}

		/** Vérification de type du tiers **/
		if (!typetiersRepository.existsById(tiersUpdate.getTypeTiers())) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}

		TypeTiers typeTiers = typetiersRepository.getOne(tiersUpdate.getTypeTiers());

		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();

		for (Integer commercialId : tiersUpdate.getCommercials()) {
			if (commercialRepository.existsById(commercialId)) {
				commercials.add(commercialRepository.getOne(commercialId));
			} else {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
		}

		/** Modifier **/
		Locality locality = modelMapper.map(tiersUpdate.getLocality(), Locality.class);
		locality.setId(tiersOld.getLocality().getId());

		Tiers tiers = modelMapper.map(tiersUpdate, Tiers.class);
		tiers.setLocality(locality);
		tiers.setCommercials(commercials);
		tiers.setTypeTiers(typeTiers);

		tiersRepository.save(tiers);

		return modelMapper.map(tiers, TiersInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification de tiers **/
		Tiers tiers = tiersRepository.findById(id).orElse(null);

		if (tiers == null) {
			throw new ResourceNotFoundException("Tiers non trouvé !");
		}

		this.checkTiersInAffiliateCommercials(tiers);

		if(tiers.getTypeTiers().getId() == 18){
			try {
				/** Se détacher de l'evenement **/
				 EventTiers eventTiers = eventTiersRepository.findByTiersId(id);
				 if(eventTiers != null){
					 eventTiersRepository.delete(eventTiers);
				 }
				tiersRepository.deleteById(id);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		try {
			/** Se détacher du projet **/
			for (Project project : tiers.getProjects()) {
				project.getTiers().remove(tiers);
			}

			tiersRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TiersInfoDto getOne(Integer id) {
		/** Vérification de tiers **/
		Tiers tiers = tiersRepository.findById(id).orElse(null);

		if (tiers == null) {
			throw new ResourceNotFoundException("Tiers non trouvé !");
		}

		this.checkTiersInAffiliateCommercials(tiers);

		return modelMapper.map(tiers, TiersInfoDto.class);
	}

	@Override
	public PageDto<TiersInfoDto> getPage(int page, int size, String search) {
		Page<Tiers> tiers = tiersRepository.findAllByOrderByCreateAtDesc(new PageRequest(page, size), search);
		List<TiersInfoDto> content = Utils.map(modelMapper, tiers.getContent(), TiersInfoDto.class);
		return new PageDto<TiersInfoDto>(content, tiers.getTotalPages(), tiers.getTotalElements(), tiers.getSize(),
				tiers.getNumber(), tiers.getNumberOfElements(), tiers.isFirst(), tiers.isLast());
	}

	@Override
	public List<TiersInfoDto> getListByTypes(List<String> typesTier) {
		List<Tiers> tiers = null;
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();

		if (commercials == null) {
			tiers = tiersRepository.findByTypes(typesTier);
		} else {
			Set<Integer> commercialsId = commercials.stream().map(Commercial::getId).collect(Collectors.toSet());
			tiers = tiersRepository.findByTypesAndCommercials(typesTier, commercialsId);
		}

		return Utils.map(modelMapper, tiers, TiersInfoDto.class);
	}

	@Override
	public List<TiersInfoDto> getList() {
		return Utils.map(modelMapper, tiersRepository.findAll(), TiersInfoDto.class);
	}


	@Override
	public TiersInfoDto assignment(Integer id, Integer commercialId) {

		/**
		 * Vérification de tier
		 */
		if (!tiersRepository.existsById(id)) {
			throw new ResourceNotFoundException("Tiers non trouvé !");
		}
		
		Tiers tiers = tiersRepository.getOne(id);

		/**
		 * Vérification de commercial
		 */
		Optional<Commercial> commercial = tiers.getCommercials().stream().filter(c -> c.getId() == commercialId)
				.findFirst();

		if (commercial.isPresent())
			throw new ResourceAlreadyExistException("Commercial déja affecté !");

		/**
		 * Affecter
		 */
		
		if (!commercialRepository.existsById(commercialId)) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}
		
		Commercial newCommercial = commercialRepository.getOne(commercialId);

		tiers.getCommercials().add(newCommercial);

		tiersRepository.save(tiers);

		/**
		 * Retour data
		 */
		return modelMapper.map(tiers, TiersInfoDto.class);
	}
	@Override
	public List<TiersInfoDto> getListByTypesAndCommercial(List<String> typesTier, Integer commercialId) {
		/** Vérification de commercial **/
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, tiersRepository.findByTypesAndCommercial(typesTier, commercialId),
				TiersInfoDto.class);
	}

	@Override
	public List<TiersInfoDto> getListByCommercial(int commercialId) {
		/** Vérification de commercial **/
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, tiersRepository.findByCommercial(commercialId), TiersInfoDto.class);
	}

	@Override
	public List<StatisticInfoDto> getStatistics(DateInterval dateInterval) {
		return tiersRepository.getStatistics(dateInterval.getStartDate(), dateInterval.getEndDate());
	}



}
