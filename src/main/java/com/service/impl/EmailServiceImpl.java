package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.EmailAddDto;
import com.dto.EmailInfoDto;
import com.dto.EmailUpdateDto;
import com.dto.PageDto;
import com.entity.Email;
import com.entity.Site;
import com.entity.Society;
import com.exception.ResourceNotFoundException;
import com.repository.EmailRepository;
import com.repository.SiteRepository;
import com.repository.SocietyRepository;
import com.service.EmailService;
import com.util.Utils;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private SocietyRepository societyRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public EmailInfoDto add(EmailAddDto emailAdd) {

		/**
		 * Vérification de site du l'émail
		 */
		if (!siteRepository.existsById(emailAdd.getSite())) {
			throw new ResourceNotFoundException("Site non trouvé !");
		}

		Site site = siteRepository.getOne(emailAdd.getSite());

		/**
		 * Ajouter
		 */
		Email email = modelMapper.map(emailAdd, Email.class);
		email.setId(null);
		email.setSite(site);

		emailRepository.save(email);

		/**
		 * Retourne data
		 */
		return modelMapper.map(email, EmailInfoDto.class);
	}

	@Override
	@Transactional
	public EmailInfoDto update(EmailUpdateDto emailUpdate) {
		/**
		 * Vérification de l'émail
		 */
		if (!emailRepository.existsById(emailUpdate.getId()))
			throw new ResourceNotFoundException("Email non trouvé !");

		/**
		 * Vérification de site du l'émail
		 */
		if (!siteRepository.existsById(emailUpdate.getSite())) {
			throw new ResourceNotFoundException("Site non trouvé !");
		}

		/**
		 * Modifier
		 */
		Site site = siteRepository.getOne(emailUpdate.getSite());
		Email email = modelMapper.map(emailUpdate, Email.class);
		email.setSite(site);

		emailRepository.save(email);

		/**
		 * Retourne data
		 */
		return modelMapper.map(email, EmailInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de l'émail
		 */
		if (!emailRepository.existsById(id))
			throw new ResourceNotFoundException("Email non trouvé !");

		try {
			/**
			 * Supprimer
			 */
			emailRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public EmailInfoDto getOne(Integer id) {
		/**
		 * Vérification de l'émail
		 */
		if (!emailRepository.existsById(id)) {
			throw new ResourceNotFoundException("Email non trouvé !");
		}

		Email email = emailRepository.getOne(id);

		return modelMapper.map(email, EmailInfoDto.class);
	}

	@Override
	public PageDto<EmailInfoDto> getPage(int page, int size, Integer societyId) {

		/**
		 * Vérification de societé
		 */
		if (!societyRepository.existsById(societyId)) {
			throw new ResourceNotFoundException("Societé non trouvé !");
		}

		Society society = societyRepository.getOne(societyId);

		Page<Email> emails = emailRepository.findBySociety(society, new PageRequest(page, size));
		List<EmailInfoDto> content = Utils.map(modelMapper, emails.getContent(), EmailInfoDto.class);
		return new PageDto<EmailInfoDto>(content, emails.getTotalPages(), emails.getTotalElements(), emails.getSize(),
				emails.getNumber(), emails.getNumberOfElements(), emails.isFirst(), emails.isLast());
	}

}
