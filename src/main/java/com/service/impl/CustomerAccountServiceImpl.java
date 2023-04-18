package com.service.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.constants.WorkspaceConstant;
import com.dto.CustomerAccountAddDto;
import com.dto.CustomerAccountInfoDto;
import com.entity.Commercial;
import com.entity.CustomerAccount;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceNotFoundException;
import com.repository.CommercialRepository;
import com.repository.CustomerAccountRepository;
import com.service.CustomerAccountService;
import com.util.Utils;
import com.util.WorkspaceUtils;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

	@Autowired
	private CommercialRepository commercialRepository;
	
	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	ModelMapper modelMapper = new ModelMapper();

	@Override
	@Transactional
	public CustomerAccountInfoDto addCustomerAccount(CustomerAccountAddDto customerAccountPayload) {
		if (customerAccountPayload == null)
			throw new ResourceNotFoundException(" customer Account body empty !");
		
		customerAccountPayload.setCreateAt(new Date());
		
		/** Vérification de projet **/
		if (customerAccountRepository.existsByReference(customerAccountPayload.getReference()))
			throw new ResourceAlreadyExistException("Reference existe déja !");

		Commercial commercial = null;

		if (customerAccountPayload.getCommercial() != null)
			commercial = commercialRepository.getOne(customerAccountPayload.getCommercial());
		else
			throw new ResourceNotFoundException("Commercial non trouvé !");


		CustomerAccount customerAccount = this.modelMapper.map(customerAccountPayload, CustomerAccount.class);
		customerAccount.setId(null);
		customerAccount.setCommercial(commercial);

		boolean isSignatureDemanderSaved = false;
		boolean isSignatureDirecteurSaved = false;

		if (customerAccountPayload.getSignatureDemander() != null) {
			isSignatureDemanderSaved = WorkspaceUtils.saveFile(
					WorkspaceConstant.generateFolderCustomerSignatureUri(commercial.getId(), customerAccount.getReference()),customerAccountPayload.getSignatureDemander(),
					WorkspaceConstant.CUSTOMER_SIGNATURE);
			customerAccount.setHaveSignatureDemander(isSignatureDemanderSaved);
		}
		
		if (customerAccountPayload.getSignatureDirecteur() != null) {
			isSignatureDirecteurSaved = WorkspaceUtils.saveFile(
					WorkspaceConstant.generateFolderCommercialSignatureUri(commercial.getId(), customerAccount.getReference()), customerAccountPayload.getSignatureDirecteur(),
					WorkspaceConstant.COMMERCIAL_SIGNATURE);
			customerAccount.setHaveSignatureDirecteur(isSignatureDirecteurSaved);
		}
		
		customerAccountRepository.save(customerAccount);

		return this.modelMapper.map(customerAccount, CustomerAccountInfoDto.class);
	}
	
	@Override
	public List<CustomerAccountInfoDto> getList() {
		List<CustomerAccount> customerAccounts = customerAccountRepository.findAll();
		return Utils.map(modelMapper, customerAccounts, CustomerAccountInfoDto.class);
	}

}
