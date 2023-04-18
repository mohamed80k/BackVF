package com.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DateInterval;
import com.dto.PageDto;
import com.dto.ProjectAddDto;
import com.dto.ProjectInfoDto;
import com.dto.ProjectUpdateDto;
import com.dto.report.ProjectReportDto;
import com.entity.Commercial;
import com.entity.Locality;
import com.entity.Lotissement;
import com.entity.Project;
import com.entity.Site;
import com.entity.StateProject;
import com.entity.Tiers;
import com.entity.TypeProject;
import com.entity.type.StatusProject;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.LotissementRepository;
import com.repository.ProjectRepository;
import com.repository.SiteRepository;
import com.repository.StateProjectRepository;
import com.repository.TiersRepository;
import com.repository.TypeProjectRepository;
import com.service.CommercialService;
import com.service.ProjectService;
import com.util.Utils;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TypeProjectRepository typeProjectRepository;
	
	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private StateProjectRepository stateProjectRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private TiersRepository<Tiers> tierRepository;

	@Autowired
	private CommercialService commercialService;

	@Autowired
	private LotissementRepository LotissementRepository;


	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public ProjectInfoDto add(ProjectAddDto projectAdd) {

		/** Vérification des dates **/
		Date date = new Date();

		if (projectAdd.getCreateAt() == null) {
			projectAdd.setCreateAt(date);
		} else if (projectAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du projet incorrect !");
		}

		/** Vérification de projet **/
		if (projectRepository.existsByName(projectAdd.getName()))
			throw new ResourceAlreadyExistException("Projet existe déja !");

		/** Vérification de type du projet **/
		if (!typeProjectRepository.existsById(projectAdd.getTypeProject())) {
			throw new ResourceNotFoundException("Type de projet non trouvé !");
		}

		TypeProject typeProject = typeProjectRepository.getOne(projectAdd.getTypeProject());

		/** Vérification de l'etat du projet **/
		if (!stateProjectRepository.existsById(projectAdd.getStateProject())) {
			throw new ResourceNotFoundException("Etat de projet non trouvé !");
		}

		StateProject stateProject = stateProjectRepository.getOne(projectAdd.getStateProject());
		
		/** Vérification de site du projet **/
		if (!siteRepository.existsById(projectAdd.getSiteProject())) {
			throw new ResourceNotFoundException("Site de projet non trouvé !");
		}

		Site siteProject = siteRepository.getOne(projectAdd.getSiteProject());
		
		Lotissement projectLotissement = null;
		
		if(projectAdd.getProjectLotissement() != null)
		projectLotissement = LotissementRepository.getOne(projectAdd.getProjectLotissement());


		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();

		for (Integer commercialId : projectAdd.getCommercials()) {
			if (commercialRepository.existsById(commercialId)) {
				commercials.add(commercialRepository.getOne(commercialId));
			} else {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
		}

		/** Vérification des tiers **/
		Set<Tiers> tiers = new HashSet<Tiers>();

		for (Integer tierId : projectAdd.getTiers()) {
			if (tierRepository.existsById(tierId)) {
				tiers.add(tierRepository.getOne(tierId));
			} else {
				throw new ResourceNotFoundException("Tiers non trouvé !");
			}
		}

		if (projectAdd.getStatus().equals(StatusProject.Concurrent)) {
			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Client")
							|| tier.getTypeTiers().getName().equals("Maitre d'oeuvre")
							|| tier.getTypeTiers().getName().equals("Architecte")
							|| tier.getTypeTiers().getName().equals("Bureau d'etude")
							|| tier.getTypeTiers().getName().equals("Bureau de contrôle")
							|| tier.getTypeTiers().getName().equals("Laboratoire")
							|| tier.getTypeTiers().getName().equals("Concurrent"))
					.collect(Collectors.toSet());
		} else {
			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Client")
							|| tier.getTypeTiers().getName().equals("Maitre d'oeuvre")
							|| tier.getTypeTiers().getName().equals("Architecte")
							|| tier.getTypeTiers().getName().equals("Bureau d'etude")
							|| tier.getTypeTiers().getName().equals("Bureau de contrôle")
							|| tier.getTypeTiers().getName().equals("Laboratoire"))
					.collect(Collectors.toSet());
		}

		/** Ajouter **/
		Locality locality = modelMapper.map(projectAdd.getLocality(), Locality.class);
		locality.setId(null);

		Project project = modelMapper.map(projectAdd, Project.class);
		project.setId(null);
		project.setLocality(locality);
		project.setCommercials(commercials);
		project.setTiers(tiers);
		project.setTypeProject(typeProject);
		project.setStateProject(stateProject);
		project.setSiteProject(siteProject);
		project.setProjectLotissement(projectLotissement);
		
		if(project.getStateProject().getName().equals("CLOTURE")) {
			project.setClosedAt(date);
		}

		projectRepository.save(project);

		return modelMapper.map(project, ProjectInfoDto.class);
	}

	@Override
	public boolean checkProjectInAffiliateCommercials(Project project) {
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();
		
		if (commercials == null) {
			return true;
		}
		
		for (Commercial commerial : commercials) {
			if (project.getCommercials().contains(commerial)) {
				return true;
			}
		}
		
		throw new ResourceNotFoundException("projet non accessible !");
	}

	@Override
	@Transactional
	public ProjectInfoDto update(ProjectUpdateDto projectUpdate) {

		/** Vérification de projet **/
		Project projectOld = projectRepository.findById(projectUpdate.getId()).orElse(null);
		if (projectOld == null) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}
 
		this.checkProjectInAffiliateCommercials(projectOld);

		if (!projectOld.getName().equals(projectUpdate.getName())
				&& projectRepository.existsByName(projectUpdate.getName()))
			throw new ResourceAlreadyExistException("Projet existe déja !");

		/** Vérification des dates **/
		Date date = new Date();

		if (projectUpdate.getCreateAt() == null) {
			projectUpdate.setCreateAt(date);
		} else if (projectUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du projet incorrect !");
		}

		/** Vérification de type du projet **/
		if (!typeProjectRepository.existsById(projectUpdate.getTypeProject())) {
			throw new ResourceNotFoundException("Type de projet non trouvé !");
		}

		TypeProject typeProject = typeProjectRepository.getOne(projectUpdate.getTypeProject());

		/** Vérification de l'etat du projet **/
		if (!stateProjectRepository.existsById(projectUpdate.getStateProject())) {
			throw new ResourceNotFoundException("Etat de projet non trouvé !");
		}
		
		Lotissement projectLotissement = null;
		
		if(projectUpdate.getProjectLotissement() != null)
		projectLotissement = LotissementRepository.getOne(projectUpdate.getProjectLotissement());

		StateProject stateProject = stateProjectRepository.getOne(projectUpdate.getStateProject());
		
		/** Vérification de site du projet **/
		if (!siteRepository.existsById(projectUpdate.getSiteProject())) {
			throw new ResourceNotFoundException("Site de projet non trouvé !");
		}

		Site siteProject = siteRepository.getOne(projectUpdate.getSiteProject());

		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();

		for (Integer commercialId : projectUpdate.getCommercials()) {
			if (commercialRepository.existsById(commercialId)) {
				commercials.add(commercialRepository.getOne(commercialId));
			} else {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
		}

		/** Vérification des tiers **/
		Set<Tiers> tiers = new HashSet<Tiers>();

		for (Integer tierId : projectUpdate.getTiers()) {
			if (tierRepository.existsById(tierId)) {
				tiers.add(tierRepository.getOne(tierId));
			} else {
				throw new ResourceNotFoundException("Tiers non trouvé !");
			}
		}

		if (projectUpdate.getStatus().name().equals("Concurrent")) {
			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Client")
							|| tier.getTypeTiers().getName().equals("Maitre d'oeuvre")
							|| tier.getTypeTiers().getName().equals("Architecte")
							|| tier.getTypeTiers().getName().equals("Bureau d'etude")
							|| tier.getTypeTiers().getName().equals("Bureau de contrôle")
							|| tier.getTypeTiers().getName().equals("Laboratoire")
							|| tier.getTypeTiers().getName().equals("Concurrent"))
					.collect(Collectors.toSet());
		} else {
			tiers = tiers.stream()
					.filter(tier -> tier.getTypeTiers().getName().equals("Client")
							|| tier.getTypeTiers().getName().equals("Maitre d'oeuvre")
							|| tier.getTypeTiers().getName().equals("Architecte")
							|| tier.getTypeTiers().getName().equals("Bureau d'etude")
							|| tier.getTypeTiers().getName().equals("Bureau de contrôle")
							|| tier.getTypeTiers().getName().equals("Laboratoire"))
					.collect(Collectors.toSet());
		}	
		
		/** Modifier **/
		Locality locality = modelMapper.map(projectUpdate.getLocality(), Locality.class);
		locality.setId(projectOld.getLocality().getId());

		Project project = modelMapper.map(projectUpdate, Project.class);
		project.setLocality(locality);
		project.setCommercials(commercials);
		project.setTiers(tiers);
		project.setTypeProject(typeProject);
		project.setStateProject(stateProject);
		project.setSiteProject(siteProject);
		project.setProjectLotissement(projectLotissement);
		
		if(!projectOld.getStatus().getValue().equals(project.getStatus().getValue())) {
			project.setPreviousStatus(projectOld.getStatus().getValue());
			project.setDateChangedStatus(date);
		}
		
		if(!projectOld.getStateProject().getName().equals("CLOTURE") && project.getStateProject().getName().equals("CLOTURE")) {
			project.setClosedAt(date);
		}

		projectRepository.save(project);

		return modelMapper.map(project, ProjectInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification de projet **/
		 Project  project = projectRepository.findById(id).orElse(null);

		if (project == null) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		this.checkProjectInAffiliateCommercials(project);
 
		try {
			projectRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ProjectInfoDto getOne(Integer id) {

		/** Vérification de projet **/
		 Project  project = projectRepository.findById(id).orElse(null);

		if (project == null) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		this.checkProjectInAffiliateCommercials(project);

		return modelMapper.map(project, ProjectInfoDto.class);
	}

	@Override
	public PageDto<ProjectInfoDto> getPage(int page, int size, String search) {

		Page<Project> projects = projectRepository.findAllByOrderByCreateAtDesc(new PageRequest(page, size), search);

		List<ProjectInfoDto> content = Utils.map(modelMapper, projects.getContent(), ProjectInfoDto.class);

		return new PageDto<ProjectInfoDto>(content, projects.getTotalPages(), projects.getTotalElements(),
				projects.getSize(), projects.getNumber(), projects.getNumberOfElements(), projects.isFirst(),
				projects.isLast());
	}

	@Override
	public List<ProjectInfoDto> getList() {
		return Utils.map(modelMapper, projectRepository.findAll(), ProjectInfoDto.class);
	}

	@Override
	public ProjectInfoDto assignment(Integer id, Integer commercialId) {

		/**
		 * Vérification de projet
		 */
		if (!projectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}
		
		Project project = projectRepository.getOne(id);

		/**
		 * Vérification de commercial
		 */
		Optional<Commercial> commercial = project.getCommercials().stream().filter(c -> c.getId() == commercialId)
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

		project.getCommercials().add(newCommercial);

		projectRepository.save(project);

		/**
		 * Retour data
		 */
		return modelMapper.map(project, ProjectInfoDto.class);

	}

	@Override
	public List<ProjectInfoDto> getListByCommercial(Integer commercialId) {
        
		List<Project> projects = null;
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();

		if (commercials == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		} else {
			Set<Integer> commercialsId = commercials.stream().map(Commercial::getId).collect(Collectors.toSet());
			projects = projectRepository.findByCommercials(commercialsId);
		}

		return Utils.map(modelMapper,projects, ProjectInfoDto.class);
	}

	@Override
	public List<ProjectReportDto> getStatistics(DateInterval dateInterval) {
		List<ProjectReportDto> statistics = projectRepository.getStatistics(dateInterval.getStartDate(),
				dateInterval.getEndDate());
		return statistics;
	}
	
	@Override
	public List<ProjectReportDto> getStatisticsBySite(DateInterval dateInterval) {
		List<ProjectReportDto> statistics = projectRepository.getStatisticsBySite(dateInterval.getStartDate(),
				dateInterval.getEndDate());
		return statistics;
	}

	@Override
	public List<ProjectReportDto> getStatisticsByCommercial(DateInterval dateInterval) {
		List<ProjectReportDto> statistics = projectRepository.getStatisticsByCommercial(dateInterval.getStartDate(),
				dateInterval.getEndDate());
		return statistics;
	}
	
	@Override
	@Transactional
	public Boolean deleteMultiple(List<Integer> projectsId) {
		try {
			for(Integer id : projectsId) {
				this.delete(id);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}