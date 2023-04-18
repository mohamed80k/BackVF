package com.service.impl;

import java.util.List;

import com.entity.*;
import com.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.SiteAddDto;
import com.dto.SiteInfoDto;
import com.dto.SiteUpdateDto;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.SiteRepository;
import com.repository.SocietyRepository;
import com.service.SiteService;
import com.util.Utils;

@Service
public class SiteServiceImpl implements SiteService {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private SocietyRepository societyRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public SiteInfoDto add(SiteAddDto siteAdd) {
		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(siteAdd.getSociety())) {
			throw new ResourceNotFoundException("Societé non trouvé !");
		}

		if(!regionRepository.existsById(siteAdd.getRegion())){
			throw new ResourceNotFoundException("la region n'existe pas");
		}
		Region region = regionRepository.getOne(siteAdd.getRegion());

		/**
		 * Ajouter
		 */
		Locality locality = modelMapper.map(siteAdd.getLocality(), Locality.class);
		locality.setId(null);
		System.out.println(locality.toString());
		Society society = societyRepository.getOne(siteAdd.getSociety());


		Site site = modelMapper.map(siteAdd, Site.class);
		site.setId(null);
		site.setLocality(locality);
		site.setRegions(region);
		site.setSociety(society);

		siteRepository.save(site);

		/**
		 * Retourne data
		 */
		return modelMapper.map(site, SiteInfoDto.class);
	}

	@Override
	@Transactional
	public SiteInfoDto update(SiteUpdateDto siteUpdate) {



		if(!regionRepository.existsById(siteUpdate.getRegion())){
			throw new ResourceNotFoundException("la region n'existe pas");
		}
		Region r = regionRepository.getOne(siteUpdate.getRegion());
		/**
		 * Vérification de site
		 */
		if (!siteRepository.existsById(siteUpdate.getId())) {
			throw new ResourceNotFoundException("Site non trouvé !");
		}
		
		Site siteOld = siteRepository.getOne(siteUpdate.getId());

		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(siteUpdate.getSociety())) {
			throw new ResourceNotFoundException("Societé non trouvé !");
		}
		
		Society society = societyRepository.getOne(siteUpdate.getSociety());

		/**
		 * Se détacher du commercial
		 */
//		if (siteOld.getSociety().getId() != siteUpdate.getSociety()) {
//			for (Commercial commercial : siteOld.getCommercials()) {
//				if (commercial.getSites().size() > 1) {
//					commercial.getSites().remove(siteOld);
//				} else {
//					throw new ResourceAlreadyExistException("Site déja utilisé !");
//				}
//			}
//		}

		/**
		 * Modifier
		 */
		Locality locality = modelMapper.map(siteUpdate.getLocality(), Locality.class);
		locality.setId(siteOld.getLocality().getId());

		Site site = modelMapper.map(siteUpdate, Site.class);
		site.setLocality(locality);
		site.setRegions(r);
		site.setSociety(society);

		siteRepository.save(site);

		/**
		 * Retourne data
		 */
		return modelMapper.map(site, SiteInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de site
		 */
		if (!siteRepository.existsById(id)) {
			throw new ResourceNotFoundException("Site non trouvé !");
		}
		
		Site site = siteRepository.getOne(id);

		/**
		 * Se détacher du commercial
		 */
//		for (Commercial commercial : site.getCommercials()) {
//			if (commercial.getSites().size() > 1) {
//				commercial.getSites().remove(site);
//			} else {
//				throw new ResourceAlreadyExistException("Site déja utilisé !");
//			}
//		}

		try {
			/**
			 * Supprimer
			 */
			siteRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public SiteInfoDto getOne(Integer id) {
		/**
		 * Vérification de site
		 */
		if (!siteRepository.existsById(id)) {
			throw new ResourceNotFoundException("Site non trouvé !");
		}
		
		Site site = siteRepository.getOne(id);

		return modelMapper.map(site, SiteInfoDto.class);
	}

	@Override
	public List<SiteInfoDto> getList() {
		return Utils.map(modelMapper, siteRepository.findAll(), SiteInfoDto.class);
	}

	@Override
	public List<SiteInfoDto> getListBySociety(Integer societyId) {
		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(societyId))
			throw new ResourceNotFoundException("Societé non trouvé !");

		/**
		 * Retourne data
		 */
		return Utils.map(modelMapper, siteRepository.findBySociety(societyId), SiteInfoDto.class);
	}

//	@Override
//	public List<SiteInfoDto> getListBySocietyAndTypeAndCommercial(Integer societyId, String type, int commercialId) {
//
//		/**
//		 * Vérification de societé
//		 */
//		if (!societyRepository.existsById(societyId))
//			throw new ResourceNotFoundException("Societé non trouvé !");
//
//		/**
//		 * Vérification de commercial
//		 */
//		if (!commercialRepository.existsById(commercialId)) {
//			throw new ResourceNotFoundException("Commercial non trouvé !");
//		}
//
//		Commercial commercial = commercialRepository.getOne(commercialId);
//
//		/**
//		 * Retourne data
//		 */
//		if (type.equals("expedition")) {
//			return Utils.map(modelMapper, siteRepository.findExpeditionBySocietyAndCommercial(societyId, commercial),
//					SiteInfoDto.class);
//		} else if (type.equals("sale")) {
//			return Utils.map(modelMapper, siteRepository.findSaleBySocietyAndCommercial(societyId, commercial),
//					SiteInfoDto.class);
//		} else {
//			throw new ResourceNotFoundException("Type de site incorrect !");
//		}
//
//	}

	@Override
	public List<SiteInfoDto> getListBySocietyAndType(Integer societyId, String type) {
		/**
		 * Vérification de societé boolean expedition, boolean sale
		 */
		if (!societyRepository.existsById(societyId))
			throw new ResourceNotFoundException("Societé non trouvé !");

		if (type.equals("expedition")) {
			return Utils.map(modelMapper, siteRepository.findExpeditionBySociety(societyId), SiteInfoDto.class);
		} else if (type.equals("sale")) {
			return Utils.map(modelMapper, siteRepository.findSaleBySociety(societyId), SiteInfoDto.class);
		} else {
			throw new ResourceNotFoundException("Type de site incorrect !");
		}

	}

	@Override
	public List<SiteInfoDto> getListBySocietyAndTypeAndCommercial(Integer societyId, String type, int commercialId) {
		return null;
	}

}
