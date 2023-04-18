package com.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.ExportDto;
import com.dto.ImportResponseDto;
import com.entity.type.ImportType;
import com.service.ExportService;
import com.service.ImportService;
import com.util.Utils;


@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class ExportImportController {

	@Autowired
	private ExportService exportService;

	@Autowired
	private ImportService importService;

	@RequestMapping(value = "/exports", method = RequestMethod.POST)
	public void exportFile(@Valid @RequestBody ExportDto exportDto, HttpServletResponse response) {
		Workbook workbook = exportService.exportFile(exportDto);
		Utils.upResponse(workbook, response);
	}

	@RequestMapping(value = "/imports", method = RequestMethod.POST)
	public List<ImportResponseDto> importFile(@RequestParam("importType") ImportType importType, @RequestParam("uploadedFile") MultipartFile uploadedFile, @RequestParam(value="societyId", required=false) Integer societyId) {
		return importService.importFile(importType, uploadedFile, societyId);
	}

	@RequestMapping(value = "/exports/pdf", method = RequestMethod.GET)
	public ByteArrayInputStream exportDataCustomerAccountPDF(HttpServletResponse response, @RequestParam(value = "customerAccountId") Integer customerAccountId) throws IOException {

		ByteArrayInputStream pdf = null;

		pdf = exportService.customerAccountParReference(customerAccountId);

		if (pdf != null) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
			FileCopyUtils.copy(pdf, response.getOutputStream());
		}
		return pdf;
	}

}
