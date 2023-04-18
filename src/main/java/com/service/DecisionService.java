package com.service;

import java.util.List;

import com.dto.DecisionAddDto;
import com.dto.DecisionInfoDto;
import com.dto.DecisionUpdateDto;
import com.dto.PageDto;

public interface DecisionService {

	public DecisionInfoDto add(DecisionAddDto decisionAdd);

	public DecisionInfoDto update(DecisionUpdateDto decisionUpdate);

	public boolean delete(Integer id);

	public DecisionInfoDto getOne(Integer id);

	public PageDto<DecisionInfoDto> getPage(int page, int size, String search);
	
	public List<DecisionInfoDto> getList();
	
	public List<DecisionInfoDto> getListByCommercial(int commercialId);
	
	public List<DecisionInfoDto> getListGroupByName();
	
	public DecisionInfoDto editStatusDecision(Integer id, boolean done);
}
