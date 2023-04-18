package com.service;

import java.util.List;

import com.dto.TypeCustomerAddDto;
import com.dto.TypeCustomerInfoDto;
import com.dto.TypeCustomerUpdateDto;

public interface TypeCustomerService {

	public TypeCustomerInfoDto add(TypeCustomerAddDto typeCustomerAdd);

	public TypeCustomerInfoDto update(TypeCustomerUpdateDto typeCustomerUpdate);

	public boolean delete(Integer id);

	public TypeCustomerInfoDto getOne(Integer id);

	public List<TypeCustomerInfoDto> getList();
}
