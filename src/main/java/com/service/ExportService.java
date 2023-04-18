package com.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

import com.dto.ExportDto;

public interface ExportService {
	
	public Workbook exportFile(ExportDto exportDto);

	ByteArrayInputStream customerAccountParReference(Integer customerAccountId) throws IOException;
}
