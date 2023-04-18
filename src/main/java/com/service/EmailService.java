package com.service;

import com.dto.EmailAddDto;
import com.dto.EmailInfoDto;
import com.dto.EmailUpdateDto;
import com.dto.PageDto;

public interface EmailService {

	public EmailInfoDto add(EmailAddDto emailAdd);

	public EmailInfoDto update(EmailUpdateDto emailUpdate);

	public boolean delete(Integer id);

	public EmailInfoDto getOne(Integer id);

	public PageDto<EmailInfoDto> getPage(int page, int size, Integer societyId);
}
