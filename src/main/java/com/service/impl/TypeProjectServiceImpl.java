package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.TypeProjectAddDto;
import com.dto.TypeProjectInfoDto;
import com.dto.TypeProjectUpdateDto;
import com.entity.TypeProject;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.ProjectRepository;
import com.repository.TypeProjectRepository;
import com.service.TypeProjectService;
import com.util.Utils;

@Service
public class TypeProjectServiceImpl implements TypeProjectService {

	@Autowired
	private TypeProjectRepository typeProjectRepository;

	@Autowired
	private ProjectRepository projectRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public TypeProjectInfoDto add(TypeProjectAddDto typeProjectAdd) {
		/**
		 * Vérification de type du projet
		 */
		if (typeProjectRepository.existsByName(typeProjectAdd.getName()))
			throw new ResourceAlreadyExistException("Type de projet existe déja !");

		/**
		 * Ajouter
		 */
		TypeProject typeProject = modelMapper.map(typeProjectAdd, TypeProject.class);
		typeProjectRepository.save(typeProject);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeProject, TypeProjectInfoDto.class);
	}

	@Override
	@Transactional
	public TypeProjectInfoDto update(TypeProjectUpdateDto typeProjectUpdate) {
		/**
		 * Vérification de type de projet
		 */
		if (!typeProjectRepository.existsById(typeProjectUpdate.getId())) { 
			throw new ResourceNotFoundException("Type de projet non trouvé !");
		}

		TypeProject typeProjectOld = typeProjectRepository.getOne(typeProjectUpdate.getId());
		
		if (!typeProjectOld.getName().equals(typeProjectUpdate.getName())
				&& typeProjectRepository.existsByName(typeProjectUpdate.getName()))
			throw new ResourceAlreadyExistException("Type de projet existe déja !");

		/**
		 * Modifier
		 */
		TypeProject typeProject = modelMapper.map(typeProjectUpdate, TypeProject.class);
		typeProjectRepository.save(typeProject);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeProject, TypeProjectInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de type de projet
		 */
		if (!typeProjectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de projet non trouvé !");
		}
		
		TypeProject typeProject = typeProjectRepository.getOne(id);

		if (projectRepository.existsByTypeProject(typeProject))
			throw new ResourceAlreadyExistException("Type de projet déja utilisé !");

		try {
			/**
			 * Supprimer
			 */
			typeProjectRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TypeProjectInfoDto getOne(Integer id) {
		/**
		 * Vérification de type du projet
		 */
		if (!typeProjectRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de projet non trouvé !");
		}

		TypeProject typeProject = typeProjectRepository.getOne(id);
		
		return modelMapper.map(typeProject, TypeProjectInfoDto.class);
	}

	@Override
	public List<TypeProjectInfoDto> getList() {
		return Utils.map(modelMapper, typeProjectRepository.findAll(), TypeProjectInfoDto.class);
	}

}
