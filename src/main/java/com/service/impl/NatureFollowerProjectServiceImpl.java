package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.NatureFollowerProjectAddDto;
import com.dto.NatureFollowerProjectInfoDto;
import com.dto.NatureFollowerProjectUpdateDto;
import com.entity.NatureFollowerProject;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.FollowerProjectRepository;
import com.repository.NatureFollowerProjectRepository;
import com.service.NatureFollowerProjectService;
import com.util.Utils;

@Service
public class NatureFollowerProjectServiceImpl implements NatureFollowerProjectService {

	@Autowired
	private NatureFollowerProjectRepository natureFollowerRepository;

	@Autowired
	private FollowerProjectRepository followerProjectRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<NatureFollowerProjectInfoDto> getList() {
		return Utils.map(modelMapper, natureFollowerRepository.findAll(), NatureFollowerProjectInfoDto.class);
	}

	@Override
	public NatureFollowerProjectInfoDto add(NatureFollowerProjectAddDto natureAdd) {

		/** Vérification de type du tiers */
		if (natureFollowerRepository.existsByName(natureAdd.getName()))
			throw new ResourceAlreadyExistException("Nature de suivi projet existe déja !");

		/** Ajouter */
		NatureFollowerProject natureFollowerProject = modelMapper.map(natureAdd, NatureFollowerProject.class);
		natureFollowerRepository.save(natureFollowerProject);

		return modelMapper.map(natureFollowerProject, NatureFollowerProjectInfoDto.class);
	}

	@Override
	public NatureFollowerProjectInfoDto update(NatureFollowerProjectUpdateDto natureUpdate) {

		/** Vérification de nature de suivi projet */
		NatureFollowerProject natureFollowerProjectOld = natureFollowerRepository.findById(natureUpdate.getId()).orElse(null);
		
		if (natureFollowerProjectOld == null) {
			throw new ResourceNotFoundException("Nature suivi de projet non trouvé !");
		}

		if (!natureFollowerProjectOld.getName().equals(natureUpdate.getName())
				&& natureFollowerRepository.existsByName(natureUpdate.getName()))
			throw new ResourceAlreadyExistException("Nature suivi de projet existe déja !");

		/** Modifier */
		NatureFollowerProject natureFollowerProject = modelMapper.map(natureUpdate, NatureFollowerProject.class);
		natureFollowerRepository.save(natureFollowerProject);

		return modelMapper.map(natureFollowerProject, NatureFollowerProjectInfoDto.class);
	}

	@Override
	public boolean delete(Integer id) {

		/** Vérification de nature de suivi projet */
		NatureFollowerProject natureFollower = natureFollowerRepository.findById(id).orElse(null);
		
		if (natureFollower == null) {
			throw new ResourceNotFoundException("Nature suivi de projet non trouvé !");
		}

		if (followerProjectRepository.existsByNatureFollower(natureFollower))
			throw new ResourceAlreadyExistException("Nature de suivi projet déja utilisé !");

		try {
			/** Supprimer */
			natureFollowerRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public NatureFollowerProjectInfoDto getOne(Integer id) {

		NatureFollowerProject natureFollower = natureFollowerRepository.findById(id).orElse(null);

		if (natureFollower == null) {
			throw new ResourceNotFoundException("Nature suivi de projet non trouvé !");
		}

		return modelMapper.map(natureFollower, NatureFollowerProjectInfoDto.class);
	}
}
