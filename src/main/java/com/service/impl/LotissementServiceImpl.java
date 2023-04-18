package com.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.LotissementAddDto;
import com.dto.LotissementInfoDto;
import com.dto.LotissementUpdateDto;
import com.dto.PageDto;
import com.entity.Locality;
import com.entity.Lotissement;
import com.entity.Project;
import com.entity.Tiers;
import com.entity.TypeProject;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.LotissementRepository;
import com.repository.ProjectRepository;
import com.repository.TiersRepository;
import com.repository.TypeProjectRepository;
import com.service.LotissementService;
import com.util.Utils;

@Service
public class LotissementServiceImpl implements LotissementService {

	@Autowired
	private LotissementRepository LotissementRepository;

	@Autowired
	private TiersRepository<Tiers> tierRepository;
	
	@Autowired
	private TypeProjectRepository typeProjectRepository;


	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public LotissementInfoDto add(LotissementAddDto LotissementAdd) {

		/** Vérification de type du projet **/
		if (!typeProjectRepository.existsById(LotissementAdd.getTypeLotissement())) {
			throw new ResourceNotFoundException("Type de lotissement non trouvé !");
		}

		TypeProject typeLotissement = typeProjectRepository.getOne(LotissementAdd.getTypeLotissement());
	

		/** Vérification des tiers **/
		Set<Tiers> tiers = new HashSet<Tiers>();

		for (Integer tierId : LotissementAdd.getTiers()) {
			if (tierRepository.existsById(tierId)) {
				tiers.add(tierRepository.getOne(tierId));
			} else {
				throw new ResourceNotFoundException("Tiers non trouvé !");
			}
		}


			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Maitre d'oeuvre"))
					.collect(Collectors.toSet());
		

		/** Ajouter **/
		Locality locality = modelMapper.map(LotissementAdd.getLocality(), Locality.class);
		locality.setId(null);

		Lotissement Lotissement = modelMapper.map(LotissementAdd, Lotissement.class);
		Lotissement.setId(null);
		Lotissement.setLocality(locality);
		Lotissement.setTiers(tiers);
		Lotissement.setTypeLotissement(typeLotissement);
		LotissementRepository.save(Lotissement);

		return modelMapper.map(Lotissement, LotissementInfoDto.class);
	}

	@Override
	@Transactional
	public LotissementInfoDto update(LotissementUpdateDto LotissementUpdate) {

		/** Vérification de projet **/
		Lotissement LotissementOld = LotissementRepository.findById(LotissementUpdate.getId()).orElse(null);
		if (LotissementOld == null) {
			throw new ResourceNotFoundException("Lotissement non trouvé !");
		}
 

		if (!LotissementOld.getName().equals(LotissementUpdate.getName())
				&& LotissementRepository.existsByName(LotissementUpdate.getName()))
			throw new ResourceAlreadyExistException("Lotissement existe déja !");


		/** Vérification de type du projet **/
		if (!typeProjectRepository.existsById(LotissementUpdate.getTypeLotissement())) {
			throw new ResourceNotFoundException("Type de lotissement non trouvé !");
		}

		TypeProject typeLotissement = typeProjectRepository.getOne(LotissementUpdate.getTypeLotissement());

		/** Vérification des tiers **/
		Set<Tiers> tiers = new HashSet<Tiers>();

		for (Integer tierId : LotissementUpdate.getTiers()) {
			if (tierRepository.existsById(tierId)) {
				tiers.add(tierRepository.getOne(tierId));
			} else {
				throw new ResourceNotFoundException("Tiers non trouvé !");
			}
		}


			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Maitre d'oeuvre"))
					.collect(Collectors.toSet());
		

		/** Modifier **/
		Locality locality = modelMapper.map(LotissementUpdate.getLocality(), Locality.class);
		locality.setId(LotissementOld.getLocality().getId());
		Lotissement Lotissement = modelMapper.map(LotissementUpdate, Lotissement.class);
		Lotissement.setLocality(locality);
		Lotissement.setTiers(tiers);
		Lotissement.setTypeLotissement(typeLotissement);
		LotissementRepository.save(Lotissement);

		return modelMapper.map(Lotissement, LotissementInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification de projet **/
		 Lotissement  Lotissement = LotissementRepository.findById(id).orElse(null);

		if (Lotissement == null) {
			throw new ResourceNotFoundException("Lotissement non trouvé !");
		}
 
		try {
			LotissementRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public LotissementInfoDto getOne(Integer id) {

		/** Vérification de projet **/
		 Lotissement  Lotissement = LotissementRepository.findById(id).orElse(null);

		if (Lotissement == null) {
			throw new ResourceNotFoundException("Lotissement non trouvé !");
		}

		return modelMapper.map(Lotissement, LotissementInfoDto.class);
	}

	@Override
	public PageDto<LotissementInfoDto> getPage(int page, int size, String search) {

		Page<Lotissement> Lotissements = LotissementRepository.findAllLotissements(new PageRequest(page, size), search);

		List<LotissementInfoDto> content = Utils.map(modelMapper, Lotissements.getContent(), LotissementInfoDto.class);

		return new PageDto<LotissementInfoDto>(content, Lotissements.getTotalPages(), Lotissements.getTotalElements(),
				Lotissements.getSize(), Lotissements.getNumber(), Lotissements.getNumberOfElements(), Lotissements.isFirst(),
				Lotissements.isLast());
	}

	@Override
	public List<LotissementInfoDto> getList() {
		return Utils.map(modelMapper, LotissementRepository.findAll(), LotissementInfoDto.class);
	}


}