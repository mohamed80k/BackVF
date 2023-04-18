package com.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.CustomerAddDto;
import com.dto.CustomerInfoDto;
import com.dto.CustomerUpdateDto;
import com.dto.PageDto;
import com.entity.Commercial;
import com.entity.Customer;
import com.entity.Locality;
import com.entity.Project;
import com.entity.Tiers;
import com.entity.TypeCustomer;
import com.entity.TypeTiers;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.CustomerRepository;
import com.repository.TiersRepository;
import com.repository.TypeCustomerRepository;
import com.repository.TypeTiersRepository;
import com.service.CommercialService;
import com.service.CustomerService;
import com.util.Utils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TiersRepository<Tiers> tiersRepository;

	@Autowired
	private TypeCustomerRepository typeCustomerRepository;

	@Autowired
	private TypeTiersRepository typeTiersRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private CommercialService commercialService;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public CustomerInfoDto add(CustomerAddDto customerAdd) {

		/** Vérification des dates **/
		Date date = new Date();

		if (customerAdd.getCreateAt() == null) {
			customerAdd.setCreateAt(date);
		} else if (customerAdd.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du client incorrect !");
		}

		/** Vérification de client **/
		if (customerRepository.existsByName(customerAdd.getName()))
			throw new ResourceAlreadyExistException("Client existe déja !");

		/** Vérification de type du client **/
		if (!typeCustomerRepository.existsById(customerAdd.getTypeCustomer())) {
			throw new ResourceNotFoundException("Type de client non trouvé !");
		}

		/** Obtenir le type du tiers **/
		String tiersTypeName = "Client";

		if (!typeTiersRepository.existsByName(tiersTypeName)) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}

		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();
		for (Integer commercialId : customerAdd.getCommercials()) {
			if (!commercialRepository.existsById(commercialId)) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
			commercials.add(commercialRepository.getOne(commercialId));
		}

		/** Ajouter **/
		TypeCustomer typeCustomer = typeCustomerRepository.getOne(customerAdd.getTypeCustomer());
		TypeTiers typeTiers = typeTiersRepository.findByName(tiersTypeName);

		Locality locality = modelMapper.map(customerAdd.getLocality(), Locality.class);
		locality.setId(null);

		Customer customer = modelMapper.map(customerAdd, Customer.class);
		customer.setId(null);
		customer.setLocality(locality);
		customer.setCommercials(commercials);
		customer.setTypeTiers(typeTiers);
		customer.setTypeCustomer(typeCustomer);

		customerRepository.save(customer);

		return modelMapper.map(customer, CustomerInfoDto.class);
	}

	@Override
	public boolean checkCustomerInAffiliateCommercials(Customer customer) {
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();
		if (commercials == null) {
			return true;
		}
		for (Commercial commerial : commercials) {
			if (customer.getCommercials().contains(commerial)) {
				return true;
			}
		}
		throw new ResourceNotFoundException("Client non accessible !");
	}

	@Override
	@Transactional
	public CustomerInfoDto update(CustomerUpdateDto customerUpdate) {

		/** Vérification des dates **/
		Date date = new Date();

		if (customerUpdate.getCreateAt() == null) {
			customerUpdate.setCreateAt(date);
		} else if (customerUpdate.getCreateAt().compareTo(date) == 1) {
			throw new ResourceConflictException("Date création du client incorrect !");
		}

		/** Vérification de client **/
		Customer customerOld = new Customer();

		if (customerRepository.existsById(customerUpdate.getId())) {
			customerOld = customerRepository.getOne(customerUpdate.getId());
			this.checkCustomerInAffiliateCommercials(customerOld);
		} else if (tiersRepository.existsById(customerUpdate.getId())) {
			customerOld = modelMapper.map(tiersRepository.getOne(customerUpdate.getId()), Customer.class);
			this.checkCustomerInAffiliateCommercials(customerOld);

			/** Supprimer le dernier tiers **/
			tiersRepository.deleteById(customerUpdate.getId());
			tiersRepository.flush();

		} else {
			throw new ResourceNotFoundException("Client non trouvé !");
		}

		/** Se détacher de projet **/
		for (Project project : customerOld.getProjects()) {
			project.getTiers().remove(customerOld);
		}

		/** Vérification de type du client **/
		if (!typeCustomerRepository.existsById(customerUpdate.getTypeCustomer())) {
			throw new ResourceNotFoundException("Type de client non trouvé !");
		}

		/** Obtenir le type du tiers **/
		String tiersName = "Client";
		if (!typeTiersRepository.existsByName(tiersName)) {
			throw new ResourceNotFoundException("Type de tiers non trouvé !");
		}

		/** Vérification des commerciaux **/
		Set<Commercial> commercials = new HashSet<Commercial>();
		for (Integer commercialId : customerUpdate.getCommercials()) {
			if (!commercialRepository.existsById(commercialId)) {
				throw new ResourceNotFoundException("Commercial non trouvé !");
			}
			commercials.add(commercialRepository.getOne(commercialId));
		}

		/** Modifier **/
		TypeTiers typeTiers = typeTiersRepository.findByName(tiersName);
		TypeCustomer typeCustomer = typeCustomerRepository.getOne(customerUpdate.getTypeCustomer());

		Locality locality = modelMapper.map(customerUpdate.getLocality(), Locality.class);
		locality.setId(null);

		Customer customer = modelMapper.map(customerUpdate, Customer.class);

		customer.setLocality(locality);
		customer.setCommercials(commercials);
		customer.setTypeTiers(typeTiers);
		customer.setTypeCustomer(typeCustomer);
		customer.setId(customerOld.getId());

		customerRepository.save(customer);

		return modelMapper.map(customer, CustomerInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		/** Vérification de client **/
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new ResourceNotFoundException("Client non trouvé !");
		}

		try {
			/** Se détacher du projet **/
			for (Project project : customer.getProjects()) {
				project.getTiers().remove(customer);
			}
			customerRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CustomerInfoDto getOne(Integer id) {
		/** Vérification de tiers **/
		Customer customer = customerRepository.findById(id).orElse(null);
		if (customer == null) {
			throw new ResourceNotFoundException("Client non trouvé !");
		}

		return modelMapper.map(customer, CustomerInfoDto.class);
	}

	@Override
	public PageDto<CustomerInfoDto> getPage(int page, int size) {
		Page<Customer> customers = customerRepository.findAll(new PageRequest(page, size));
		List<CustomerInfoDto> content = Utils.map(modelMapper, customers.getContent(), CustomerInfoDto.class);
		return new PageDto<CustomerInfoDto>(content, customers.getTotalPages(), customers.getTotalElements(),
				customers.getSize(), customers.getNumber(), customers.getNumberOfElements(), customers.isFirst(),
				customers.isLast());
	}

	@Override
	public List<CustomerInfoDto> getList() {
		List<Customer> customers = null;
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();
		if (commercials == null) {
			customers = customerRepository.findAll();
		} else {
			Set<Integer> commercialsId = commercials.stream().map(Commercial::getId).collect(Collectors.toSet());
			customers = customerRepository.findCustomersByCommercials(commercialsId);
		}
 
		return Utils.map(modelMapper, customers, CustomerInfoDto.class);
	}
	
	@Override
	public List<CustomerInfoDto> getListRevendeurs() {	
		return Utils.map(modelMapper, customerRepository.findAllRevendeurs(), CustomerInfoDto.class);
	}

	@Override
	public List<CustomerInfoDto> getListByCommercial(Integer commercialId) {
		/** Vérification de commercial **/
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		List<String> typesTiers = Arrays.asList("Client");

		return Utils.map(modelMapper, customerRepository.findByTypesAndCommercial(typesTiers, commercialId),
				CustomerInfoDto.class);
	}
}
