package com.service;

import java.util.List;

import com.dto.CustomerAccountAddDto;
import com.dto.CustomerAccountInfoDto;

public interface CustomerAccountService {
	public CustomerAccountInfoDto addCustomerAccount(CustomerAccountAddDto customerAccountPayload);
    public List<CustomerAccountInfoDto> getList();

}
