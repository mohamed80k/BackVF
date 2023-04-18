package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CategoryAddDto;
import com.dto.CategoryInfoDto;
import com.dto.CategoryUpdateDto;
import com.entity.Category;
import com.entity.Society;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.ArticleRepository;
import com.repository.CategoryRepository;
import com.repository.SocietyRepository;
import com.service.CategoryService;
import com.util.Utils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private SocietyRepository societyRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public CategoryInfoDto add(CategoryAddDto categoryAdd) {
		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(categoryAdd.getSociety())) {
			throw new ResourceNotFoundException("Societé non trouvé !");
		}
		
		/**
		 * Vérification de catégorie
		 */
		if (categoryRepository.existsByName(categoryAdd.getName()))
			throw new ResourceAlreadyExistException("Catégorie existe déja !");

		Society society = societyRepository.getOne(categoryAdd.getSociety());
		
		/**
		 * Ajouter
		 */
		Category category = modelMapper.map(categoryAdd, Category.class);
		category.setId(null);
		category.setSociety(society);

		categoryRepository.save(category);

		/**
		 * Retourne data
		 */
		return modelMapper.map(category, CategoryInfoDto.class);
	}

	@Override
	@Transactional
	public CategoryInfoDto update(CategoryUpdateDto categoryUpdate) {
		/**
		 * Vérification de catégorie
		 */
		if (!categoryRepository.existsById(categoryUpdate.getId())) {
			throw new ResourceNotFoundException("Catégorie non trouvé !");
		}

		Category categoryOld = categoryRepository.getOne(categoryUpdate.getId());
		
		if (!categoryOld.getName().equals(categoryUpdate.getName())
				&& categoryRepository.existsByName(categoryUpdate.getName()))
			throw new ResourceAlreadyExistException("Catégorie existe déja !");

		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(categoryUpdate.getSociety())) {
			throw new ResourceNotFoundException("Societé non trouvée !");
		}
		
		Society society = societyRepository.getOne(categoryUpdate.getSociety());
		
		/**
		 * Modifier
		 */
		Category category = modelMapper.map(categoryUpdate, Category.class);
		category.setSociety(society);
		
		categoryRepository.save(category);

		/**
		 * Retourne data
		 */
		return modelMapper.map(category, CategoryInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de catégorie
		 */
		if (!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Catégorie non trouvée !");
		}
		
		Category category = categoryRepository.getOne(id);

		if (articleRepository.existsByCategory(category))
			throw new ResourceAlreadyExistException("Catégorie déja utilisée !");

		try {
			/**
			 * Supprimer
			 */
			categoryRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CategoryInfoDto getOne(Integer id) {
		/**
		 * Vérification de catégorie
		 */
		if (!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Catégorie non trouvé !");
		}
		
		Category category = categoryRepository.getOne(id);

		return modelMapper.map(category, CategoryInfoDto.class);
	}

	@Override
	public List<CategoryInfoDto> getList() {
		return Utils.map(modelMapper, categoryRepository.findAll(), CategoryInfoDto.class);
	}

	@Override
	public List<CategoryInfoDto> getListBySociety(Integer societyId) {
		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(societyId)) {
			throw new ResourceNotFoundException("Societé non trouvé !");
		}

		Society society = societyRepository.getOne(societyId);
		
		/**
		 * Retourne data
		 */
		return Utils.map(modelMapper, categoryRepository.findBySociety(society), CategoryInfoDto.class);
	}

}
