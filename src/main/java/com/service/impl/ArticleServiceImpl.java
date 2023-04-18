package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ArticleAddDto;
import com.dto.ArticleInfoDto;
import com.dto.ArticleUpdateDto;
import com.dto.PageDto;
import com.entity.Article;
import com.entity.Category;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.ArticleRepository;
import com.repository.CategoryRepository;
import com.repository.SocietyRepository;
import com.service.ArticleService;
import com.util.Utils;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SocietyRepository societyRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public ArticleInfoDto add(ArticleAddDto articleAdd) {
		/**
		 * Vérification de l'article
		 */
		if (articleRepository.existsByReference(articleAdd.getReference()))
			throw new ResourceAlreadyExistException("Article existe déja !");

		/**
		 * Vérification de catégorie du l'article
		 */
		if (!categoryRepository.existsById(articleAdd.getCategory())) {
			throw new ResourceNotFoundException("Catégorie non trouvé !");
		}

		Category category = categoryRepository.getOne(articleAdd.getCategory());

		/**
		 * Ajouter
		 */
		Article article = modelMapper.map(articleAdd, Article.class);
		article.setId(null);
		article.setCategory(category);

		articleRepository.save(article);

		/**
		 * Retourne data
		 */
		return modelMapper.map(article, ArticleInfoDto.class);
	}

	@Override
	@Transactional
	public ArticleInfoDto update(ArticleUpdateDto articleUpdate) {
		/**
		 * Vérification de l'article
		 */
		if (!articleRepository.existsById(articleUpdate.getId())) {
			throw new ResourceNotFoundException("Article non trouvé !");
		}

		Article articleOld = articleRepository.getOne(articleUpdate.getId());

		if (!articleOld.getReference().equals(articleUpdate.getReference())
				&& articleRepository.existsByReference(articleUpdate.getReference()))
			throw new ResourceAlreadyExistException("Article existe déja !");

		/**
		 * Vérification de catégorie du l'article
		 */
		if (!categoryRepository.existsById(articleUpdate.getCategory())) {
			throw new ResourceNotFoundException("Catégorie non trouvé !");
		}

		Category category = categoryRepository.getOne(articleUpdate.getCategory());

		/**
		 * Modifier
		 */
		Article article = modelMapper.map(articleUpdate, Article.class);
		article.setCategory(category);

		articleRepository.save(article);

		/**
		 * Retourne data
		 */
		return modelMapper.map(article, ArticleInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de l'article
		 */
		if (!articleRepository.existsById(id)) {
			throw new ResourceNotFoundException("Article non trouvé !");
		}

		Article article = articleRepository.getOne(id);

		/**
		 * Se détacher du vente
		 */
//		for (Sale sale : article.getSales()) {
//			if (sale.getArticles().size() > 1) {
//				sale.getArticles().remove(article);
//			} else {
//				throw new ResourceAlreadyExistException("Article déja utilisé !");
//			}
//		}

		try {
			/**
			 * Supprimer
			 */
			articleRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArticleInfoDto getOne(Integer id) {
		/**
		 * Vérification de l'article
		 */
		if (!articleRepository.existsById(id)) {
			throw new ResourceNotFoundException("Article non trouvé !");
		}

		Article article = articleRepository.getOne(id);

		return modelMapper.map(article, ArticleInfoDto.class);
	}

	@Override
	public PageDto<ArticleInfoDto> getPage(int page, int size, Integer societyId) {
		/**
		 * Vérification de societé
		 */

		if (!societyRepository.existsById(societyId))
			throw new ResourceNotFoundException("Societé non trouvé !");

		Page<Article> articles = articleRepository.findBySociety(societyId, new PageRequest(page, size));

		List<ArticleInfoDto> content = Utils.map(modelMapper, articles.getContent(), ArticleInfoDto.class);
		return new PageDto<ArticleInfoDto>(content, articles.getTotalPages(), articles.getTotalElements(),
				articles.getSize(), articles.getNumber(), articles.getNumberOfElements(), articles.isFirst(),
				articles.isLast());
	}

	@Override
	public List<ArticleInfoDto> getListBySociety(Integer societyId) {
		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(societyId))
			throw new ResourceNotFoundException("Societé non trouvé !");

		return Utils.map(modelMapper, articleRepository.findBySociety(societyId), ArticleInfoDto.class);
	}

	@Override
	public List<ArticleInfoDto> getListByCategory(Integer categoryId) {
		/**
		 * Vérification de catégorie
		 */
		if (!categoryRepository.existsById(categoryId))
			throw new ResourceNotFoundException("Catégorie non trouvée !");

		return Utils.map(modelMapper, articleRepository.findByCategory(categoryId), ArticleInfoDto.class);
	}
}
