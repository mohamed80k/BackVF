package com.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.dto.*;
import com.entity.*;
import com.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.type.UserType;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.service.CommercialService;
import com.service.UserService;
import com.util.Utils;

@Service
public class CommercialServiceImpl implements CommercialService {
	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private SocietyRepository societyRepository;

	@Autowired
	UserService userService;

	@Autowired
	private UserRepository userRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public CommercialInfoDto add(CommercialAddDto commercialAdd) {

		/** Vérification de commercial **/
		if (commercialRepository.existsByName(commercialAdd.getName()))
			throw new ResourceAlreadyExistException("Commercial existe déja !");

		/** Vérification des sites **/
//		Set<Site> sites = new HashSet<Site>();
//
//		for (Integer siteId : commercialAdd.getSites()) {
//			if (siteRepository.existsById(siteId)) {
//				sites.add(siteRepository.getOne(siteId));
//			} else {
//				throw new ResourceNotFoundException("Site non trouvé !");
//			}
//		}
		/** Vérification du region **/
         if(!regionRepository.existsById(commercialAdd.getRegion())){
			 throw new ResourceNotFoundException("region non trouvé");
		 }
		Region region = regionRepository.getOne(commercialAdd.getRegion());
		/** Ajouter **/
		Commercial commercial = modelMapper.map(commercialAdd, Commercial.class);
//		commercial.setSites(sites);
        commercial.setRegion(region);
		commercialRepository.save(commercial);
		CommercialInfoDto commercialInfoDto = modelMapper.map(commercial, CommercialInfoDto.class);
		System.out.println(commercialInfoDto.toString());
		return commercialInfoDto;
	}

	@Override
	public boolean checkCommercialInAffiliateCommercials(Commercial commercial) {
		List<Commercial> commercials = this.getAffiliateCommercials();

		if (commercials == null) {
			return true;
		}

		if (commercials.contains(commercial)) {
			return true;
		}

		throw new ResourceNotFoundException("projet non accessible !");
	}

	@Override
	@Transactional
	public CommercialInfoDto update(CommercialUpdateDto commercialUpdate) {

		/** Vérification de commercial **/
		Commercial commercialOld = commercialRepository.findById(commercialUpdate.getId()).orElse(null);

		if (commercialOld == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		this.checkCommercialInAffiliateCommercials(commercialOld);

		if (!commercialOld.getName().equals(commercialUpdate.getName())
				&& commercialRepository.existsByName(commercialUpdate.getName())) {
			throw new ResourceAlreadyExistException("Commercial existe déja !");
		}

//		/** Vérification des sites **/
//		Set<Site> sites = new HashSet<Site>();
//
//		for (Integer siteId : commercialUpdate.getSites()) {
//			if (siteRepository.existsById(siteId)) {
//				sites.add(siteRepository.getOne(siteId));
//			} else {
//				throw new ResourceNotFoundException("Site non trouvé !");
//			}
//		}
/** Vérification du region **/
		if(regionRepository.existsById(commercialUpdate.getRegion())){
			throw new ResourceNotFoundException("region non trouvé");
		}
		Region region = regionRepository.getOne(commercialUpdate.getRegion());
		/** Modifier **/
		Commercial commercial = modelMapper.map(commercialUpdate, Commercial.class);
//		commercial.setSites(sites);

		commercialRepository.save(commercial);

		return modelMapper.map(commercial, CommercialInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {

		/** Vérification de commercial **/
		Commercial commercial = commercialRepository.findById(id).orElse(null);

		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		this.checkCommercialInAffiliateCommercials(commercial);

		try {
			/** Se détacher du projet **/
			for (Project project : commercial.getProjets()) {
				project.getCommercials().remove(commercial);
			}

			/** Se détacher tiers **/
			for (Tiers tier : commercial.getTiers()) {
				tier.getCommercials().remove(commercial);
			}

			/** Supprimer **/
			commercialRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public CommercialInfoDto getOne(Integer id) {

		/** Vérification de projet **/
		Commercial commercial = commercialRepository.findById(id).orElse(null);

		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		this.checkCommercialInAffiliateCommercials(commercial);

		return modelMapper.map(commercial, CommercialInfoDto.class);
	}

	@Override
	public PageDto<CommercialInfoDto> getPageBySociety(int page, int size, Integer societyId, String search) {
		return null;
	}

//	@Override
//	public PageDto<CommercialInfoDto> getPageBySociety(int page, int size, Integer societyId, String search) {
//
//		/** Vérification de societé **/
//		if (!societyRepository.existsById(societyId))
//			throw new ResourceNotFoundException("Societé non trouvé !");
//
//		Page<Commercial> commercials = commercialRepository.findBySociety(societyId, new PageRequest(page, size), search);
//		List<CommercialInfoDto> content = Utils.map(modelMapper, commercials.getContent(), CommercialInfoDto.class);
//		return new PageDto<CommercialInfoDto>(content, commercials.getTotalPages(), commercials.getTotalElements(),
//				commercials.getSize(), commercials.getNumber(), commercials.getNumberOfElements(),
//				commercials.isFirst(), commercials.isLast());
//	}

	@Override
	public List<CommercialInfoDto> getList() {
		return Utils.map(modelMapper, commercialRepository.findAll(), CommercialInfoDto.class);
	}

	@Override
	public List<CommercialInfoDto> getListBySociety(Integer societyId) {
		return null;
	}


//	@Override
//	public List<CommercialInfoDto> getListBySociety(Integer societyId) {
//
//		/** Vérification de societé **/
//		if (!societyRepository.existsById(societyId))
//			throw new ResourceNotFoundException("Societé non trouvé !");
//
//		List<Commercial> commercials = null;
//		List<Commercial> affiliateCommercials = this.getAffiliateCommercials();
//
//		if (affiliateCommercials == null) {
//			commercials = commercialRepository.findBySociety(societyId);
//		} else {
//			Set<Integer> commercialsId = affiliateCommercials.stream().map(Commercial::getId)
//					.collect(Collectors.toSet());
//			commercials = commercialRepository.findBySocietyAndCommercials(societyId, commercialsId);
//		}
//
//		Collections.sort(commercials, Commercial.IdComparator);
//
//		return Utils.map(modelMapper, commercials, CommercialInfoDto.class);
//	}

	@Override
	public List<Commercial> getAffiliateCommercials() {

		List<Commercial> commercials = new ArrayList<>();

		User currentUser = this.userService.whoami();

		switch (currentUser.getType()) {
		case admin:
			return null;
		case commercial:
			commercials.add(currentUser.getCommercial());
			break;
		case commercial_manager:
			commercials.add(currentUser.getCommercial());
			this.userRepository.getListByManager(currentUser.getId()).forEach(user -> {
				commercials.add(user.getCommercial());
			});
			break;
		case regional_manager:
			commercials.add(currentUser.getCommercial());
			this.userRepository.getListByManager(currentUser.getId()).forEach(commercialManagerUser -> {
				commercials.add(commercialManagerUser.getCommercial());
				if (commercialManagerUser.getType() == UserType.commercial_manager) {
					this.userRepository.getListByManager(commercialManagerUser.getId()).forEach(commercialUser -> {
						commercials.add(commercialUser.getCommercial());
					});
				}
			});
			break;
		case commercial_director:
			commercials.add(currentUser.getCommercial());
			this.userRepository.getListByManager(currentUser.getId()).forEach(regionalManagerUser -> {
				commercials.add(regionalManagerUser.getCommercial());
				if (regionalManagerUser.getType() == UserType.regional_manager) {
					this.userRepository.getListByManager(regionalManagerUser.getId()).forEach(commercialManagerUser -> {
						commercials.add(commercialManagerUser.getCommercial());
						if (commercialManagerUser.getType() == UserType.commercial_manager) {
							this.userRepository.getListByManager(commercialManagerUser.getId()).forEach(commercialUser -> {
								commercials.add(commercialUser.getCommercial());
							});
						}
					});
				}
			});
			break;
		case director_general:
			commercials.add(currentUser.getCommercial());
			commercials.addAll(this.commercialRepository.findAll());
			break;
		default:
			break;
		}

		return commercials;
	}

}
