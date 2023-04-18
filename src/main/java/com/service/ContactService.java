package com.service;

import java.util.List;

import com.dto.ContactAddDto;
import com.dto.ContactInfoDto;
import com.dto.ContactUpdateDto;
import com.dto.PageDto;

public interface ContactService {

	public ContactInfoDto add(ContactAddDto contactAdd);

	public ContactInfoDto update(ContactUpdateDto contactUpdate);

	public boolean delete(Integer id);
	
	public ContactInfoDto getOne(Integer id);

	public PageDto<ContactInfoDto> getPage(int page, int size, String search);
	
	public List<ContactInfoDto> getList();

	public List<ContactInfoDto> getListByCustomer(Integer customerId);
	
	public List<ContactInfoDto> getListByCommercial(Integer commercialId);
}
