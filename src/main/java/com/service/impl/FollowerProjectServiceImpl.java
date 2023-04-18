package com.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DateInterval;
import com.dto.FollowerProjectAddDto;
import com.dto.FollowerProjectInfoDto;
import com.dto.FollowerProjectUpdateDto;
import com.dto.PageDto;
import com.dto.report.NumberOfVisitsReportDto;
import com.entity.Commercial;
import com.entity.Customer;
import com.entity.FollowerProject;
import com.entity.NatureFollowerProject;
import com.entity.Project;
import com.entity.Tiers;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.FollowerProjectRepository;
import com.repository.NatureFollowerProjectRepository;
import com.repository.ProjectRepository;
import com.service.FollowerProjectService;
import com.util.Utils;

@Service
public class FollowerProjectServiceImpl implements FollowerProjectService {

	@Autowired
	FollowerProjectRepository followerProjectRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	CommercialRepository commercialRepository;

	@Autowired
	NatureFollowerProjectRepository natureFollowerRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public FollowerProjectInfoDto add(FollowerProjectAddDto followerProjectAdd) {

		/** Vérification des dates **/
		Date date = new Date();

		if (followerProjectAdd.getCreateAt() == null) {
			followerProjectAdd.setCreateAt(date);
		} else if (followerProjectAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date de création incorrect !");
		}

		/** Vérification de nature de suivi **/
		if (!natureFollowerRepository.existsById(followerProjectAdd.getNatureFollower())) {
			throw new ResourceNotFoundException("Nature de suivi de projet non trouvé !");
		}

		/** Vérification de projet à suivi **/
		if (!projectRepository.existsById(followerProjectAdd.getProject())) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		Project project = projectRepository.getOne(followerProjectAdd.getProject());

		/** Vérification de commercial du suivi de projet **/
		Commercial commercial = project.getCommercials().stream()
				.filter(commercialOfProject -> commercialOfProject.getId() == followerProjectAdd.getCommercial())
				.findFirst().orElse(null);
		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		/** Vérification de client du suivi de projet **/
		Tiers tiers = new Tiers();
		if (followerProjectAdd.getCustomer() != null) {
			tiers = project.getTiers().stream()
					.filter(tiersOfProject -> tiersOfProject.getId().equals(followerProjectAdd.getCustomer())
							&& tiersOfProject.getTypeTiers().getName().equals("Client"))
					.findFirst().orElse(null);
			if (tiers == null) {
				throw new ResourceNotFoundException("Client non trouvé !");
			}
		} else {
			tiers = null;
		}

		/** Ajouter **/
		NatureFollowerProject natureFollower = natureFollowerRepository.getOne(followerProjectAdd.getNatureFollower());

		FollowerProject followerProject = modelMapper.map(followerProjectAdd, FollowerProject.class);

		followerProject.setId(null);
		followerProject.setProject(project);
		followerProject.setCommercial(commercial);
		if (tiers != null)
			followerProject.setCustomer(modelMapper.map(tiers, Customer.class));
		followerProject.setNatureFollower(natureFollower);

		followerProjectRepository.save(followerProject);

		return modelMapper.map(followerProject, FollowerProjectInfoDto.class);
	}

	@Override
	@Transactional
	public FollowerProjectInfoDto update(FollowerProjectUpdateDto followerProjectUpdate) {

		/** Vérification du suivi de projet **/
		if (!followerProjectRepository.existsById(followerProjectUpdate.getId()))
			throw new ResourceNotFoundException("Suivi de projet non trouvé !");

		/** Vérification des dates **/
		Date date = new Date();

		if (followerProjectUpdate.getCreateAt() == null) {
			followerProjectUpdate.setCreateAt(date);
		} else if (followerProjectUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date de création incorrect !");
		}

		/** Vérification de nature de suivi **/
		if (!natureFollowerRepository.existsById(followerProjectUpdate.getNatureFollower())) {
			throw new ResourceNotFoundException("Nature de suivi de projet non trouvé !");
		}

		/** Vérification de projet à suivi **/
		if (!projectRepository.existsById(followerProjectUpdate.getProject())) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		Project project = projectRepository.getOne(followerProjectUpdate.getProject());

		/** Vérification de commercial du suivi de projet **/
		Commercial commercial = project.getCommercials().stream()
				.filter(commercialOfProject -> commercialOfProject.getId() == followerProjectUpdate.getCommercial())
				.findFirst().orElse(null);
		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		/** Vérification de client du suivi de projet **/
		Tiers tiers = new Tiers();
		if (followerProjectUpdate.getCustomer() != null) {
			tiers = project.getTiers().stream()
					.filter(tiersOfProject -> tiersOfProject.getId().equals(followerProjectUpdate.getCustomer())
							&& tiersOfProject.getTypeTiers().getName().equals("Client"))
					.findFirst().orElse(null);
			if (tiers == null) {
				throw new ResourceNotFoundException("Client non trouvé !");
			}
		} else {
			tiers = null;
		}

		/** Modifier **/
		NatureFollowerProject natureFollower = natureFollowerRepository
				.getOne(followerProjectUpdate.getNatureFollower());

		FollowerProject followerProject = modelMapper.map(followerProjectUpdate, FollowerProject.class);

		followerProject.setProject(project);
		followerProject.setCommercial(commercial);
		if (tiers != null)
			followerProject.setCustomer(modelMapper.map(tiers, Customer.class));
		followerProject.setNatureFollower(natureFollower);

		followerProjectRepository.save(followerProject);

		return modelMapper.map(followerProject, FollowerProjectInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification du suivi de projet **/
		if (!followerProjectRepository.existsById(id))
			throw new ResourceNotFoundException("Suivi de projet non trouvé !");

		try {
			followerProjectRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public FollowerProjectInfoDto getOne(Integer id) {
		/** Vérification du suivi de projet **/
		if (!followerProjectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Suivi de projet non trouvé !");
		}

		FollowerProject followedProject = followerProjectRepository.getOne(id);

		return modelMapper.map(followedProject, FollowerProjectInfoDto.class);
	}

	@Override
	public PageDto<FollowerProjectInfoDto> getPage(int page, int size, String search) {
		Page<FollowerProject> decisions = followerProjectRepository
				.findAllByOrderByCreateAtDesc(new PageRequest(page, size), search);
		List<FollowerProjectInfoDto> content = Utils.map(modelMapper, decisions.getContent(),
				FollowerProjectInfoDto.class);
		return new PageDto<FollowerProjectInfoDto>(content, decisions.getTotalPages(), decisions.getTotalElements(),
				decisions.getSize(), decisions.getNumber(), decisions.getNumberOfElements(), decisions.isFirst(),
				decisions.isLast());
	}

	@Override
	public List<FollowerProjectInfoDto> getListByCommercial(int commercialId) {
		/** Vérification de commercial **/
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, followerProjectRepository.findByCommercialId(commercialId),
				FollowerProjectInfoDto.class);
	}

	@Override
	public List<NumberOfVisitsReportDto> getStatistics(DateInterval dateInterval) {
		List<NumberOfVisitsReportDto> followers = this.followerProjectRepository
				.getProjectFollowerStatistics(dateInterval.getStartDate(), dateInterval.getEndDate());

		if (followers.size() == 0) {
			throw new ResourceNotFoundException("Aucun suivi trouvé dans cette période !");
		}

		return followers;
	}

	@Override
	public List<FollowerProjectInfoDto> getListByPeriod(DateInterval dateInterval) {
		List<FollowerProject> followers = followerProjectRepository.findByCreateAtBetween(dateInterval.getStartDate(),
				dateInterval.getEndDate());
		return Utils.map(modelMapper, followers, FollowerProjectInfoDto.class);
	}

}