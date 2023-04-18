package com.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.DecisionAddDto;
import com.dto.DecisionInfoDto;
import com.dto.DecisionUpdateDto;
import com.dto.PageDto;
import com.entity.Commercial;
import com.entity.Customer;
import com.entity.Decision;
import com.entity.Project;
import com.entity.Tiers;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.DecisionRepository;
import com.repository.ProjectRepository;
import com.service.DecisionService;
import com.util.Utils;

@Service
public class DecisionServiceImpl implements DecisionService {

	@Autowired
	private DecisionRepository decisionRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public DecisionInfoDto add(DecisionAddDto decisionAdd) {

		/**
		 * Vérification des dates
		 */
		Date date = new Date();

		if (decisionAdd.getCreateAt() == null) {
			decisionAdd.setCreateAt(date);
		} else if (decisionAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date de création incorrect !");
		}

		/**
		 * Vérification de projet du décision
		 */

		if (!projectRepository.existsById(decisionAdd.getProject())) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		Project project = projectRepository.getOne(decisionAdd.getProject());

		/**
		 * Vérification de commercial du décision
		 */
		Commercial commercial = project.getCommercials().stream()
				.filter(commercialOfProject -> commercialOfProject.getId() == decisionAdd.getCommercial()).findFirst()
				.orElse(null);
		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		/**
		 * Vérification de client du décision
		 */
		Tiers tiers = new Tiers();
		if (decisionAdd.getCustomer() != null) {
			tiers = project.getTiers().stream()
					.filter(tierOfProject -> tierOfProject.getId().equals(decisionAdd.getCustomer())
							&& tierOfProject.getTypeTiers().getName().equals("Client"))
					.findFirst().orElse(null);
			if (tiers == null) {
				throw new ResourceNotFoundException("Client non trouvé !");
			}

		} else {
			tiers = null;
		}

		/**
		 * Ajouter
		 */
		Decision decision = modelMapper.map(decisionAdd, Decision.class);

		decision.setId(null);
		decision.setProject(project);
		decision.setCommercial(commercial);
		if (tiers != null)
			decision.setCustomer(modelMapper.map(tiers, Customer.class));

		if (decision.isDone()) {
			decision.setDecideAt(date);
		} else {
			decision.setDecideAt(null);
		}

		decisionRepository.save(decision);

		/**
		 * Retourne data
		 */
		return modelMapper.map(decision, DecisionInfoDto.class);
	}

	@Override
	@Transactional
	public DecisionInfoDto update(DecisionUpdateDto decisionUpdate) {
		/**
		 * Vérification de décision
		 */
		if (!decisionRepository.existsById(decisionUpdate.getId()))
			throw new ResourceNotFoundException("Décision non trouvé !");

		/**
		 * Vérification des dates
		 */
		Date date = new Date();

		if (decisionUpdate.getCreateAt() == null) {
			decisionUpdate.setCreateAt(date);
		} else if (decisionUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date de création incorrect !");
		}

		/**
		 * Vérification de projet du décision
		 */

		if (!projectRepository.existsById(decisionUpdate.getProject())) {
			throw new ResourceNotFoundException("Projet non trouvé !");
		}

		Project project = projectRepository.getOne(decisionUpdate.getProject());

		/**
		 * Vérification de commercial du décision
		 */
		Commercial commercial = project.getCommercials().stream()
				.filter(commercialOfProject -> commercialOfProject.getId() == decisionUpdate.getCommercial())
				.findFirst().orElse(null);
		if (commercial == null) {
			throw new ResourceNotFoundException("Commercial non trouvé !");
		}

		/**
		 * Vérification de client du décision
		 */
		Tiers tiers = new Tiers();
		if (decisionUpdate.getCustomer() != null) {
			tiers = project.getTiers().stream()
					.filter(tierOfProject -> tierOfProject.getId().equals(decisionUpdate.getCustomer())
							&& tierOfProject.getTypeTiers().getName().equals("Client"))
					.findFirst().orElse(null);
			if (tiers == null) {
				throw new ResourceNotFoundException("Client non trouvé !");
			}
		} else {
			tiers = null;
		}

		/**
		 * Modifier
		 */
		Decision decision = modelMapper.map(decisionUpdate, Decision.class);

		decision.setProject(project);
		decision.setCommercial(commercial);
		if (tiers != null)
			decision.setCustomer(modelMapper.map(tiers, Customer.class));

		if (decision.isDone()) {
			decision.setDecideAt(date);
		} else {
			decision.setDecideAt(null);
		}

		decisionRepository.save(decision);

		/**
		 * Retourne data
		 */
		return modelMapper.map(decision, DecisionInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification décision
		 */
		if (!decisionRepository.existsById(id))
			throw new ResourceNotFoundException("Décision non trouvé !");

		try {
			/**
			 * Supprimer
			 */
			decisionRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public DecisionInfoDto getOne(Integer id) {
		/**
		 * Vérification de décision
		 */
		if (!decisionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Décision non trouvé !");
		}

		Decision decision = decisionRepository.getOne(id);

		return modelMapper.map(decision, DecisionInfoDto.class);
	}

	@Override
	public PageDto<DecisionInfoDto> getPage(int page, int size, String search) {
		Page<Decision> decisions = decisionRepository.findAllAndNameContaining(new PageRequest(page, size), search);
		List<DecisionInfoDto> content = Utils.map(modelMapper, decisions.getContent(), DecisionInfoDto.class);
		return new PageDto<DecisionInfoDto>(content, decisions.getTotalPages(), decisions.getTotalElements(),
				decisions.getSize(), decisions.getNumber(), decisions.getNumberOfElements(), decisions.isFirst(),
				decisions.isLast());
	}

	@Override
	public List<DecisionInfoDto> getListGroupByName() {
		return Utils.map(modelMapper, decisionRepository.findAllGroupByName(), DecisionInfoDto.class);
	}

	@Override
	public List<DecisionInfoDto> getList() {
		return Utils.map(modelMapper, decisionRepository.findAll(), DecisionInfoDto.class);
	}

	@Override
	public DecisionInfoDto editStatusDecision(Integer id, boolean done) {
		/**
		 * Vérification de decision
		 */
		if (decisionRepository.existsById(id)) {
			throw new ResourceNotFoundException("Décision non trouvé !");
		}

		Decision decision = decisionRepository.getOne(id);

		decision.setDone(done);

		if (done) {
			decision.setDecideAt(new Date());
		} else {
			decision.setDecideAt(null);
		}

		decisionRepository.save(decision);

		return modelMapper.map(decision, DecisionInfoDto.class);
	}

	@Override
	public List<DecisionInfoDto> getListByCommercial(int commercialId) {
		/**
		 * Vérification de commercial
		 */
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, decisionRepository.findByCommercial(commercialId), DecisionInfoDto.class);
	}

}
