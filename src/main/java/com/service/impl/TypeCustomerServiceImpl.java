package com.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.TypeCustomerAddDto;
import com.dto.TypeCustomerInfoDto;
import com.dto.TypeCustomerUpdateDto;
import com.entity.TypeCustomer;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.CustomerRepository;
import com.repository.TypeCustomerRepository;
import com.service.TypeCustomerService;
import com.util.Utils;

@Service
public class TypeCustomerServiceImpl implements TypeCustomerService {

	@Autowired
	private TypeCustomerRepository typeCustomerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public TypeCustomerInfoDto add(TypeCustomerAddDto typeCustomerAdd) {
		/**
		 * Vérification de type du client
		 */
		if (typeCustomerRepository.existsByName(typeCustomerAdd.getName()))
			throw new ResourceAlreadyExistException("Type de client existe déja !");

		/**
		 * Ajouter
		 */
		TypeCustomer typeCustomer = modelMapper.map(typeCustomerAdd, TypeCustomer.class);
		typeCustomerRepository.save(typeCustomer);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeCustomer, TypeCustomerInfoDto.class);
	}

	@Override
	@Transactional
	public TypeCustomerInfoDto update(TypeCustomerUpdateDto typeCustomerUpdate) {
		/**
		 * Vérification de type de client
		 */
		if (!typeCustomerRepository.existsById(typeCustomerUpdate.getId())) {
			throw new ResourceNotFoundException("Type de client non trouvé !");
		}
		
		TypeCustomer typeCustomerOld = typeCustomerRepository.getOne(typeCustomerUpdate.getId());

		if (!typeCustomerOld.getName().equals(typeCustomerUpdate.getName())
				&& typeCustomerRepository.existsByName(typeCustomerUpdate.getName()))
			throw new ResourceAlreadyExistException("Type de client existe déja !");

		/**
		 * Modifier
		 */
		TypeCustomer typeCustomer = modelMapper.map(typeCustomerUpdate, TypeCustomer.class);
		typeCustomerRepository.save(typeCustomer);

		/**
		 * Retourne data
		 */
		return modelMapper.map(typeCustomer, TypeCustomerInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/**
		 * Vérification de type de client
		 */
		if (!typeCustomerRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de client non trouvé !");
		}
		
		TypeCustomer typeCustomer = typeCustomerRepository.getOne(id);

		if (customerRepository.existsByTypeCustomer(typeCustomer))
			throw new ResourceAlreadyExistException("Type de client déja utilisé !");
		
		try {
			/**
			 * Supprimer
			 */
			typeCustomerRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public TypeCustomerInfoDto getOne(Integer id) {
		/**
		 * Vérification de type du client
		 */
		if (!typeCustomerRepository.existsById(id)) {
			throw new ResourceNotFoundException("Type de client non trouvé !");
		}
		
		TypeCustomer typeCustomer = typeCustomerRepository.getOne(id);

		return modelMapper.map(typeCustomer, TypeCustomerInfoDto.class);
	}

	@Override
	public List<TypeCustomerInfoDto> getList() {
		return Utils.map(modelMapper, typeCustomerRepository.findAll(), TypeCustomerInfoDto.class);
	}

}
