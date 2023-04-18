package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.StateProjectAddDto;
import com.dto.StateProjectInfoDto;
import com.dto.StateProjectUpdateDto;
import com.entity.StateProject;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.ProjectRepository;
import com.repository.StateProjectRepository;
import com.service.StateProjectService;
import com.util.Utils;

@Service
public class StateProjectServiceImpl implements StateProjectService {

	@Autowired
	private StateProjectRepository stateProjectRepository;

	@Autowired
	private ProjectRepository projectRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public StateProjectInfoDto add(StateProjectAddDto stateProjectAdd) {
		/**
		 * Vérification de l'état du projet
		 */
		if (stateProjectRepository.existsByName(stateProjectAdd.getName()))
			throw new ResourceAlreadyExistException("Etat de projet existe déja !");

		/**
		 * Ajouter
		 */
		StateProject stateProject = modelMapper.map(stateProjectAdd, StateProject.class);
		stateProjectRepository.save(stateProject);

		/**
		 * Retourne data
		 */
		return modelMapper.map(stateProject, StateProjectInfoDto.class);
	}

	@Override
	@Transactional
	public StateProjectInfoDto update(StateProjectUpdateDto stateProjectUpdate) {
		/**
		 * Vérification de l'état de projet
		 */
		if (!stateProjectRepository.existsById(stateProjectUpdate.getId())) {
			throw new ResourceNotFoundException("Etat de projet non trouvé !");
		}
		
		StateProject stateProjectOld = stateProjectRepository.getOne(stateProjectUpdate.getId());

		if (!stateProjectOld.getName().equals(stateProjectUpdate.getName())
				&& stateProjectRepository.existsByName(stateProjectUpdate.getName()))
			throw new ResourceAlreadyExistException("Etat de projet existe déja !");

		/**
		 * Modifier
		 */
		StateProject stateProject = modelMapper.map(stateProjectUpdate, StateProject.class);
		stateProjectRepository.save(stateProject);

		/**
		 * Retourne data
		 */
		return modelMapper.map(stateProject, StateProjectInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de l'état de projet
		 */
		if (!stateProjectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Etat de projet non trouvé !");
		}
		
		StateProject stateProject = stateProjectRepository.getOne(id);

		if (projectRepository.existsByStateProject(stateProject))
			throw new ResourceAlreadyExistException("Etat de projet déja utilisé !");

		try {
			/**
			 * Supprimer
			 */
			stateProjectRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public StateProjectInfoDto getOne(Integer id) {
		/**
		 * Vérification de l'état du projet
		 */
		if (!stateProjectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Etat de projet non trouvé !");
		}

		StateProject stateProject = stateProjectRepository.getOne(id);
		
		return modelMapper.map(stateProject, StateProjectInfoDto.class);
	}

	@Override
	public List<StateProjectInfoDto> getList() {
		return Utils.map(modelMapper, stateProjectRepository.findAll(), StateProjectInfoDto.class);
	}

}
