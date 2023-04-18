package com.service;

import java.util.List;

import com.dto.CustomerAddDto;
import com.dto.CustomerInfoDto;
import com.dto.CustomerUpdateDto;
import com.dto.PageDto;
import com.entity.Customer;

public interface CustomerService {

	public CustomerInfoDto add(CustomerAddDto customerAdd);

	public CustomerInfoDto update(CustomerUpdateDto customerUpdate);

	public boolean delete(Integer id);

	public CustomerInfoDto getOne(Integer id);
	
	public PageDto<CustomerInfoDto> getPage(int page, int size);

	public List<CustomerInfoDto> getList();
	
	public List<CustomerInfoDto> getListByCommercial(Integer commercialId);
	
	public boolean checkCustomerInAffiliateCommercials(Customer customer);

	List<CustomerInfoDto> getListRevendeurs();
}
