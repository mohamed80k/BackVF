package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.CustomerAccountAddDto;
import com.dto.CustomerAccountInfoDto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.CustomerAccountService;

@RestController
@RequestMapping(value = "/api/customers-accounts")
@CrossOrigin
public class CustomerAccountController {
	
	@Autowired
	CustomerAccountService customerAccountService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<CustomerAccountInfoDto> getList() {
		return customerAccountService.getList();
	}


	/**
	 * =====================================================
	 *
	 * POST CALL
	 *
	 * =====================================================
	 *
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */

	@RequestMapping(method = RequestMethod.POST)
	public CustomerAccountInfoDto addCustomerAccount(String customerAccount, @RequestParam(name = "signatureDirecteur", required = false) MultipartFile signatureDirecteur,
			@RequestParam(name = "signatureDemander", required = false) MultipartFile signatureDemander,
			HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		CustomerAccountAddDto customerAccountPayload = mapper.readValue(customerAccount, CustomerAccountAddDto.class);
		customerAccountPayload.setSignatureDemander(signatureDemander);
		customerAccountPayload.setSignatureDirecteur(signatureDirecteur);
		return customerAccountService.addCustomerAccount(customerAccountPayload);
	}

	
}
