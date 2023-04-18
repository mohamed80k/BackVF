package com.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dto.ImportResponseDto;
import com.entity.type.ImportType;

public interface ImportService {

	public List<ImportResponseDto> importFile(ImportType importType, MultipartFile file, Integer societyId);
}
