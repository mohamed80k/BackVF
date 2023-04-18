package com.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.ContactAddDto;
import com.dto.ContactInfoDto;
import com.dto.ContactUpdateDto;
import com.dto.PageDto;
import com.entity.Commercial;
import com.entity.Contact;
import com.entity.Customer;
import com.entity.Profession;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.ContactRepository;
import com.repository.CustomerRepository;
import com.repository.ProfessionRepository;
import com.service.CommercialService;
import com.service.ContactService;
import com.service.CustomerService;
import com.util.Utils;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ProfessionRepository professionRepository;

	@Autowired
	private CommercialRepository commercialRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommercialService commercialService;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public ContactInfoDto add(ContactAddDto contactAdd) {

		/** Vérification de client du contact **/
		if (!customerRepository.existsById(contactAdd.getCustomer())) {
			throw new ResourceNotFoundException("Client non trouvé !");
		}

		/** Vérification de profession du contact **/
		if (!professionRepository.existsById(contactAdd.getProfession())) {
			throw new ResourceNotFoundException("Profession non trouvé !");
		}

		/** Ajouter **/
		Customer customer = customerRepository.getOne(contactAdd.getCustomer());
		Profession profession = professionRepository.getOne(contactAdd.getProfession());

		Contact contact = modelMapper.map(contactAdd, Contact.class);
		contact.setId(null);
		contact.setCustomer(customer);
		contact.setProfession(profession);

		contactRepository.save(contact);

		return modelMapper.map(contact, ContactInfoDto.class);
	}
	


	@Override
	@Transactional
	public ContactInfoDto update(ContactUpdateDto contactUpdate) {
		/** Vérification de contact **/
		Contact contactOld = contactRepository.findById(contactUpdate.getId()).orElse(null);
		if (contactOld == null) {
			throw new ResourceNotFoundException("Contact non trouvé !");
		}
 
		this.customerService.checkCustomerInAffiliateCommercials(contactOld.getCustomer());

		/** Vérification de client du contact **/
		if (!customerRepository.existsById(contactUpdate.getCustomer())) {
			throw new ResourceAlreadyExistException("Client non trouvé !");
		}

		/** Vérification de profession du contact **/
		if (!professionRepository.existsById(contactUpdate.getProfession())) {
			throw new ResourceNotFoundException("Profession non trouvé !");
		}

		/** Modifier **/
		Customer customer = customerRepository.getOne(contactUpdate.getCustomer());
		Profession profession = professionRepository.getOne(contactUpdate.getProfession());

		Contact contact = modelMapper.map(contactUpdate, Contact.class);
		contact.setId(contactOld.getId());
		contact.setCustomer(customer);
		contact.setProfession(profession);

		contactRepository.save(contact);

		return modelMapper.map(contact, ContactInfoDto.class);
	}

	@Override
	@Transactional
	public boolean delete(Integer id) {
		
		/** Vérification de contact **/
		Contact contact = contactRepository.findById(id).orElse(null);
		if (contact == null) {
			throw new ResourceNotFoundException("Contact non trouvé !");
		}
		
		this.customerService.checkCustomerInAffiliateCommercials(contact.getCustomer());

		try {
			contactRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ContactInfoDto getOne(Integer id) {
		/** Vérification de contact **/
		Contact contact = contactRepository.findById(id).orElse(null);
		if (contact == null) {
			throw new ResourceNotFoundException("Contact non trouvé !");
		}
 
		this.customerService.checkCustomerInAffiliateCommercials(contact.getCustomer());
		
		return modelMapper.map(contact, ContactInfoDto.class);
	}

	@Override
	public PageDto<ContactInfoDto> getPage(int page, int size, String search) {
		Page<Contact> contacts = contactRepository.findAllAndNameContaining(new PageRequest(page, size), search);
		List<ContactInfoDto> content = Utils.map(modelMapper, contacts.getContent(), ContactInfoDto.class);
		return new PageDto<ContactInfoDto>(content, contacts.getTotalPages(), contacts.getTotalElements(),
				contacts.getSize(), contacts.getNumber(), contacts.getNumberOfElements(), contacts.isFirst(),
				contacts.isLast());
	}

	@Override
	public List<ContactInfoDto> getListByCustomer(Integer customerId) {
		/** Vérification de client **/
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			throw new ResourceNotFoundException("Client non trouvé !");
		}
 
		return Utils.map(modelMapper, customer.getContacts(), ContactInfoDto.class);
	}

	@Override
	public List<ContactInfoDto> getList() {
		List<Contact> contacts = null;
		
		List<Commercial> commercials = this.commercialService.getAffiliateCommercials();
		
		if (commercials == null) {
			contacts = contactRepository.findAll();
		} else {
			Set<Integer> commercialsId = commercials.stream().map(Commercial::getId).collect(Collectors.toSet());
			contacts = contactRepository.findByCommercials(commercialsId);
		}
		
		return Utils.map(modelMapper, contacts, ContactInfoDto.class);
	}

	@Override
	public List<ContactInfoDto> getListByCommercial(Integer commercialId) {
		/** Vérification de commercial **/
		if (!commercialRepository.existsById(commercialId))
			throw new ResourceNotFoundException("Commercial non trouvé !");

		return Utils.map(modelMapper, contactRepository.findByCommercial(commercialId), ContactInfoDto.class);
	}

}
