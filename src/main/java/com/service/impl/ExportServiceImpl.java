package com.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.entity.*;
import com.repository.*;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.dto.CustomerAccountInfoDto;
import com.dto.DecisionInfoDto;
import com.dto.ExportDto;
import com.dto.FollowerProjectInfoDto;
import com.dto.report.NumberOfVisitsReportDto;
import com.dto.report.ProjectStateDto;
import com.entity.type.StatusProject;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.model.itextpdfPlugin;
import com.service.ExportService;
import com.util.Utils;

@Service
public class ExportServiceImpl implements ExportService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private DecisionRepository decisionRepository;

	@Autowired
	private FollowerProjectRepository followerProjectRepository;

	@Autowired
	private TypeTiersRepository typeTiersRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TypeCustomerRepository typeCustomerRepository;

	@Autowired
	private CommercialRepository commercialRepository;

	@Autowired
	private CustomerAccountRepository customerAccountRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EventTiersRepository eventTiersRepository;

	ModelMapper modelMapper = new ModelMapper();

	private ExportDto exportDto = new ExportDto();

	@Value("${path-excel-template}")
	private String pathTemplateExcel;

	@Value("${path-excel-report}")
	private String pathReportExcel;
	
	@Value("${path-html-template}")
	private String pathTemplateHtml;
	
	@Value("${files-dir}")
	private String fileDir;

	/** DATE FORMAT **/
	SimpleDateFormat dateAndHourFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm");
	SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	/** WORKBOOK SETTINGS **/
	File file = null;
	FileInputStream fileInput = null;
	Workbook workbook = null;
	Row row = null;
	Sheet sheet = null;

	@Override
	public Workbook exportFile(ExportDto exportDto) {
		this.exportDto = exportDto;
		switch (exportDto.getExportType()) {
		case state_projects:
			return exportStateProject();
		case state_tasks:
			return exportStateTasks();
		case followed_projects:
			return exportFollowedProjects();
		case followed_projects_daily:
			return exportFollowedProjectsDaily();
		case followed_projects_by_commercial:
			return exportFollowedProjectsByCommercial();
		case customers_by_commercial:
			return exportCustomersByCommercial();
		case articles_template:
			return exportArticleTemplate();
		case tiers_template:
			return exportTiersTemplate();
		case state_projects_by_states:
			return exportStateProjectByStates();
		case states_template:
			return exportStatesTemplate();
		case projects_template:
			return exportProjectTemplate();
		case lotissements_template:
			return exportLotissementTemplate();
		case pointOfSales_template:
			return exportPointOfSalesTemplate();
		case event_visitors:
			return exportVisitorsEventTemplate();
		default:
			throw new ResourceNotFoundException("Type de rapport non trouvé !");
		}
	}

	/**
	 * ================================= RAPPORTS =================================
	 **/

	/** RAPPORT ETAT DES PROJETS **/
	public Workbook exportStateProject() {
		try {

			/**
			 * Vérification des dates
			 */
			this.validateReportDates();

			List<ProjectStateDto> projects = new ArrayList<ProjectStateDto>();
			
			List<String> statusProject = exportDto.getStatus().stream()
				    .map(StatusProject::getValue)
				    .collect(Collectors.toList());
			
			if (!exportDto.isAllSites()) {
				if (exportDto.getSites().isEmpty()) {
					return null;
				} else {
					if (!exportDto.isAllStatus()) {
						if (exportDto.getStatus().isEmpty()) {
							return null;
						} else {
							
							projects = projectRepository
									.findInSiteAndStatusProject(exportDto.getStartDate(), exportDto.getEndDate(),
											exportDto.getSites(), statusProject);
						}
					} else {
						/**
						 * Charge infos
						 */
						projects = projectRepository
								.findInSitesProjectOrderByCreatAt(exportDto.getStartDate(), exportDto.getEndDate(),
										exportDto.getSites());
					}
				}
			} else {

				if (!exportDto.isAllStatus()) {
					if (exportDto.getStatus().isEmpty()) {
						return null;
					} else {
						projects = projectRepository
								.findInStatusProjectOrderByCreatAt(exportDto.getStartDate(), exportDto.getEndDate(),
										statusProject);
					}
				} else {
					/**
					 * Charge infos
					 */
					projects = projectRepository.findAllProjectsByCreateAt(exportDto.getStartDate(), exportDto.getEndDate());
				}

			}

			if (projects.size() == 0) {
				return null;
			}
			
			/**
			 * préparation de rapport
			 */
			file = new ClassPathResource(pathReportExcel + "/state-projects.xlsx").getFile();
			fileInput = new FileInputStream(this.file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(0);

			String period = "Du: "
					+ dateAndHourFormat.format(new Date(
							exportDto.getStartDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(
							exportDto.getEndDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getEndDate())));

			this.sheet.getRow(0).getCell(22).setCellValue(period);

			int rowNumber = 4;
			
			String addressAfterProcessing, strStart = null;

			/** START **/
			for (ProjectStateDto project : projects) {

				this.row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(project.getProjectName());
				row.createCell(1).setCellValue(project.getType());
				
				try {
				strStart = project.getAddress();
				int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
				addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
				row.createCell(2).setCellValue(addressAfterProcessing);
				}catch (Exception e) {
					if (strStart != null) {
						row.createCell(2).setCellValue(strStart);
					} 
					// TODO: handle exception
				}

				if (project.getLatitude() != 0 && project.getLongitude() != 0) {
					this.createHeperlinK(row.getCell(2),
							"http://maps.google.com/?q=" + project.getLatitude() + ","
									+ project.getLongitude() + "&z=10");
				}

				row.createCell(3).setCellValue(project.getLatitude());
				row.createCell(4).setCellValue(project.getLongitude());
				row.createCell(5).setCellValue(project.getDescription());
				row.createCell(6).setCellValue(project.getClient());
				row.createCell(7).setCellValue(project.getTypeClient());
				row.createCell(8).setCellValue(project.getSegment());
				row.createCell(9).setCellValue(project.getCommercial());
				row.createCell(10).setCellValue(project.getStatus());
				row.createCell(11).setCellValue(project.getStateProject());
				row.createCell(12).setCellValue(project.getSite());
				row.createCell(13).setCellValue(project.getDas());
				row.createCell(14).setCellValue(project.getLotissement());
				row.createCell(15).setCellValue(project.getCaBpe());
				row.createCell(16).setCellValue(project.getCaPl());
				row.createCell(17).setCellValue(project.getCaMa());
				row.createCell(18).setCellValue(project.getCaPvDa());
				row.createCell(19).setCellValue(project.getCaEstime());
				row.createCell(20).setCellValue(project.getPreviousStatus());
				if (project.getClosedAt() != null)
					row.createCell(21).setCellValue(dateAndHourFormat.format(new Date(
							project.getClosedAt().getTime() + Utils.getTimeZoneOffSet(project.getClosedAt()))));
				row.createCell(22).setCellValue(dateAndHourFormat.format(
						new Date(project.getCreatedAt().getTime() + Utils.getTimeZoneOffSet(project.getCreatedAt()))));
				rowNumber++;
			}
			//this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** RAPPORT ETAT DES PROJETS par etats **/
	public Workbook exportStateProjectByStates() {
		try {
			if (exportDto.getStatesProject().isEmpty()) {
				return null;
			}

			List<Project> projects = new ArrayList<Project>();
			if (!exportDto.isAllSites()) {
				if (exportDto.getSites().isEmpty()) {
					return null;
				} else {
					/**
					 * Charge infos
					 */
					projects = projectRepository
							.findAllProjectInSiteAndStateProject(exportDto.getSites(), exportDto.getStatesProject())
							.stream().sorted(Comparator.comparing(Project::getCreateAt).reversed())
							.collect(Collectors.toList());
				}
			} else {
				/**
				 * Charge infos
				 */
				projects = projectRepository.findAllProjectInStateProject(exportDto.getStatesProject()).stream()
						.sorted(Comparator.comparing(Project::getCreateAt).reversed()).collect(Collectors.toList());
			}

			if (projects.size() == 0) {
				return null;
			}

			/**
			 * préparation de rapport
			 */
			file = new ClassPathResource(pathReportExcel + "/state-projects.xlsx").getFile();
			fileInput = new FileInputStream(this.file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(0);

			Date date = new Date();

			String period = "Exporté Le: "
					+ dateAndHourFormat.format(new Date(date.getTime() + Utils.getTimeZoneOffSet(date)));

			this.sheet.getRow(0).getCell(22).setCellValue(period);

			int rowNumber = 4;

			/** GET CLIENTS DES PROJETS **/
			Set<Customer> customers = new HashSet<>();

			Set<Integer> customersId = projects.stream().map(p -> {
				return p.getTiers().stream().map(Tiers::getId).collect(Collectors.toSet());
			}).flatMap(Set::stream).collect(Collectors.toSet());

			if (customersId.size() > 0) {
				customers = customerRepository.findByCustomersId(customersId);
			}
			
			String addressAfterProcessing, strStart = null;

			/** START **/
			for (Project project : projects) {

				this.row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(project.getName());
				row.createCell(1).setCellValue(project.getTypeProject().getName());
				
				
				try {
					strStart = project.getLocality().getAddress();
					int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
					addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
					row.createCell(2).setCellValue(addressAfterProcessing);
					}catch (Exception e) {
						if (strStart != null) {
							row.createCell(2).setCellValue(strStart);
						} 
						// TODO: handle exception
					}

				if (project.getLocality().getLatitude() != 0 || project.getLocality().getLongitude() != 0) {
					this.createHeperlinK(row.getCell(2),
							"http://maps.google.com/?q=" + project.getLocality().getLatitude() + ","
									+ project.getLocality().getLongitude() + "&z=10");
				}

				row.createCell(3).setCellValue(project.getLocality().getLatitude());
				row.createCell(4).setCellValue(project.getLocality().getLongitude());
				row.createCell(5).setCellValue(project.getDescription());

				List<String> customersName = project.getTiers().stream()
						.filter(tiers -> tiers.getTypeTiers().getName().equals("Client")).map(c -> c.getName())
						.collect(Collectors.toList());

				List<String> customersTypeName = customers.stream().filter(c -> customersName.contains(c.getName()))
						.map(c -> c.getTypeCustomer().getName()).collect(Collectors.toList());

				row.createCell(6).setCellValue(String.join(", ", customersName));
				row.createCell(7).setCellValue(String.join(", ", customersTypeName));
				if (project.getSegment() != null)
					row.createCell(8).setCellValue(project.getSegment().getValue());
				List<String> commercialsName = project.getCommercials().stream().map(c -> c.getName())
						.collect(Collectors.toList());
				row.createCell(9).setCellValue(String.join(", ", commercialsName));
				row.createCell(10).setCellValue(project.getStatus().getValue());
				row.createCell(11).setCellValue(project.getStateProject().getName());
				if (project.getSiteProject() != null)
					row.createCell(12).setCellValue(project.getSiteProject().getDesignation());
				List<String> das = project.getDas().stream().map(c -> c.getValue()).collect(Collectors.toList());
				row.createCell(13).setCellValue(String.join(", ", das));
				if (project.getProjectLotissement() != null)
					row.createCell(14).setCellValue(project.getProjectLotissement().getName());
				row.createCell(15).setCellValue(project.getCaBpe());
				row.createCell(16).setCellValue(project.getCaPl());
				row.createCell(17).setCellValue(project.getCaMa());
				row.createCell(18).setCellValue(project.getCaPl());
				row.createCell(19).setCellValue(project.getCaEstime());
				row.createCell(20).setCellValue(project.getPreviousStatus());
				if (project.getClosedAt() != null)
					row.createCell(21).setCellValue(dateAndHourFormat.format(new Date(
							project.getClosedAt().getTime() + Utils.getTimeZoneOffSet(project.getClosedAt()))));
				row.createCell(22).setCellValue(dateAndHourFormat.format(
						new Date(project.getCreateAt().getTime() + Utils.getTimeZoneOffSet(project.getCreateAt()))));

				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** RAPPORT ETAT DE TACHES **/
	public Workbook exportStateTasks() {
		try {

			/**
			 * Vérification des dates
			 */
			this.validateReportDates();

			/**
			 * Charge infos
			 */
			List<DecisionInfoDto> decisionsInfo = Utils.map(modelMapper,
					decisionRepository.finAllByCreateAt(exportDto.getStartDate(), exportDto.getEndDate()),
					DecisionInfoDto.class);

			if (decisionsInfo.size() == 0) {
				return null;
			}

			/**
			 * préparation de rapport
			 */
			file = new ClassPathResource(pathReportExcel + "/state-tasks.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(0);

			String period = "Du: "
					+ dateAndHourFormat.format(new Date(
							exportDto.getStartDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(
							exportDto.getEndDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getEndDate())));

			sheet.getRow(0).getCell(6).setCellValue(period);

			int rowNumber = 4;

			for (DecisionInfoDto decisionInfo : decisionsInfo) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(decisionInfo.getName());
				row.createCell(1).setCellValue(decisionInfo.getProject().getName());
				row.createCell(2).setCellValue(decisionInfo.getCommercial().getName());
				row.createCell(3).setCellValue(decisionInfo.getCustomer().getName());

				if (decisionInfo.isDone()) {
					row.createCell(4).setCellValue("Effectuée");
				} else {
					row.createCell(4).setCellValue("Non effectuée");
				}

				if (decisionInfo.getDecideAt() != null) {
					row.createCell(5)
							.setCellValue(dateAndHourFormat.format(new Date(decisionInfo.getDecideAt().getTime()
									+ Utils.getTimeZoneOffSet(decisionInfo.getDecideAt()))));
				}

				row.createCell(6).setCellValue(dateAndHourFormat.format(new Date(
						decisionInfo.getCreateAt().getTime() + Utils.getTimeZoneOffSet(decisionInfo.getCreateAt()))));

				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * RAPPORT SUIVI DES PROJETS
	 */
	public Workbook exportFollowedProjects() {
		try {

			/**
			 * VERIFICATION DATE INTERVAL
			 */
			this.validateReportDates();

			/**
			 * GET INFOS
			 */
			List<NumberOfVisitsReportDto> followers = followerProjectRepository
					.getProjectFollowerStatistics(exportDto.getStartDate(), exportDto.getEndDate()).stream()
					.sorted(Comparator.comparing(NumberOfVisitsReportDto::getCommercialName,
							String.CASE_INSENSITIVE_ORDER))
					.collect(Collectors.toList());

			if (followers.size() == 0) {
				return null;
			}

			/**
			 * WORKBOOK SETTINGS
			 */
			file = new ClassPathResource(pathReportExcel + "/followed-projects.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(0);

			/** PERIOD **/
			String period = "Du: "
					+ dateAndHourFormat.format(new Date(
							exportDto.getStartDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(
							exportDto.getEndDate().getTime() + Utils.getTimeZoneOffSet(exportDto.getEndDate())));

			sheet.getRow(0).getCell(9).setCellValue(period);

			/** GENERER **/
			int rowNumber = 4;
			
			String addressAfterProcessing, strStart = null;
			
			for (NumberOfVisitsReportDto follower : followers) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(follower.getCommercialName());
				row.createCell(1).setCellValue(follower.getProjectName());
				row.createCell(2).setCellValue(follower.getProjectTypeName());
				row.createCell(3).setCellValue(follower.getProjectDescription());
				
				
				try {
					strStart = follower.getProjectAddress();
					int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
					addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
					row.createCell(4).setCellValue(addressAfterProcessing);
					}catch (Exception e) {
						if (strStart != null) {
							row.createCell(4).setCellValue(strStart);
						} 
						// TODO: handle exception
					}

				if (follower.getProjectLatitude() != 0 || follower.getProjectLongitude() != 0) {
					this.createHeperlinK(row.getCell(4), "http://maps.google.com/?q=" + follower.getProjectLatitude()
							+ "," + follower.getProjectLongitude() + "&z=10");
				}

				row.createCell(5).setCellValue(follower.getCustomerName());
				row.createCell(6).setCellValue(follower.getCustomerTypeName());

				row.createCell(7).setCellValue(dateAndHourFormat.format(new Date(
						follower.getFollowedFist().getTime() + Utils.getTimeZoneOffSet(follower.getFollowedFist()))));
				row.createCell(8).setCellValue(dateAndHourFormat.format(new Date(
						follower.getFollowedLast().getTime() + Utils.getTimeZoneOffSet(follower.getFollowedFist()))));
				row.createCell(9).setCellValue(follower.getNumberOFVisits());

				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** CREATE HEPERLINK **/
	private void createHeperlinK(Cell cell, String value) {
		/** HUPER LINK STYLES **/
		CellStyle hyperLinkStyle = workbook.createCellStyle();
		Font hyperLinkStyleFont = workbook.createFont();
		hyperLinkStyleFont.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
		hyperLinkStyle.setFont(hyperLinkStyleFont);

		Hyperlink hyperlink = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
		hyperlink.setAddress(value);
		cell.setCellStyle(hyperLinkStyle);
		cell.setHyperlink(hyperlink);
	}

	/** CREATE STYLE DANGER CELL **/
	private void createDangerCelllStyle(Cell cell) {
		CellStyle dangerCellStyle = workbook.createCellStyle();
		Font dangerCellFont = workbook.createFont();
		dangerCellFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		dangerCellStyle.setFont(dangerCellFont);
		cell.setCellStyle(dangerCellStyle);
	}

	/**
	 * RAPPORT SUIVI DES PROJETS JOURNALIERE
	 */
	public Workbook exportFollowedProjectsDaily() {
		try {

			/**
			 * Vérification des dates
			 */
			this.validateReportDates();

			/**
			 * Charge infos
			 */
			List<FollowerProject> followers = followerProjectRepository.findByCreateAtBetween(exportDto.getStartDate(),
					exportDto.getEndDate());

			if (followers.size() == 0) {
				return null;
			}

			List<FollowerProjectInfoDto> followersInfoDto = Utils.map(modelMapper, followers,
					FollowerProjectInfoDto.class);

			followersInfoDto.forEach(follower -> {
				follower.setCreateAt(
						new Date(follower.getCreateAt().getTime() + Utils.getTimeZoneOffSet(follower.getCreateAt())));

				follower.setDay(Utils.removeTime(follower.getCreateAt()));
			});

			/** Follower by day **/
			Map<Date, List<FollowerProjectInfoDto>> followersByDay = followersInfoDto.stream()
					.collect(Collectors.groupingBy(FollowerProjectInfoDto::getDay));

			/**
			 * Workbook setting
			 */
			file = new ClassPathResource(pathReportExcel + "/followed-projects-daily.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);

			/**
			 * Period
			 */
			String period = "Du: "
					+ dateAndHourFormat.format(new Date(this.exportDto.getStartDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(this.exportDto.getEndDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getEndDate())));

			/**
			 * Cloner les fueilles
			 */
			int sheetNumber = 0;
			Set<Date> keys = new TreeSet<Date>(followersByDay.keySet());

			for (Date key : keys) {
				if (sheetNumber != 0) {
					sheet = workbook.cloneSheet(0);
				} else {
					sheet = workbook.getSheetAt(0);
				}
				workbook.setSheetName(sheetNumber, dateFormat.format(key));
				sheet.getRow(0).getCell(8).setCellValue(period);
				sheetNumber++;
			}

			/**
			 * Générer le rapport
			 */
			sheetNumber = 0;
			int rowNumber = 4;

			List<Commercial> commercials = this.commercialRepository.findAll().stream()
					.sorted(Commercial.NameComparator).collect(Collectors.toList());

			String addressAfterProcessing, strStart = null;
			
			for (Date key : keys) {

				sheet = workbook.getSheetAt(sheetNumber);
				rowNumber = 4;

				/**
				 * Insérer
				 */
				List<FollowerProjectInfoDto> data = followersByDay.get(key).stream()
						.sorted(Comparator.comparing(FollowerProjectInfoDto::getCreateAt).reversed())
						.collect(Collectors.toList());
				for (FollowerProjectInfoDto follower : data) {
					row = sheet.createRow(rowNumber);
					row.createCell(0).setCellValue(follower.getCommercial().getName());
					row.createCell(1).setCellValue(follower.getProject().getName());
					row.createCell(2).setCellValue(follower.getProject().getTypeProject().getName());
					row.createCell(3).setCellValue(follower.getProject().getDescription());
					
					try {
						strStart = follower.getProject().getLocality().getAddress();
						int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
						addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
						row.createCell(4).setCellValue(addressAfterProcessing);
						}catch (Exception e) {
							if (strStart != null) {
								row.createCell(4).setCellValue(strStart);
							} 
							// TODO: handle exception
						}
					
					

					if (follower.getProject().getLocality().getLatitude() != 0
							|| follower.getProject().getLocality().getLongitude() != 0) {
						this.createHeperlinK(row.getCell(4),
								"http://maps.google.com/?q=" + follower.getProject().getLocality().getLatitude() + ","
										+ follower.getProject().getLocality().getLongitude() + "&z=10");
					}

					if (follower.getCustomer() != null) {
						row.createCell(5).setCellValue(follower.getCustomer().getName());
						row.createCell(6).setCellValue(follower.getCustomer().getTypeCustomer().getName());
					}
					row.createCell(7).setCellValue(hourFormat.format(follower.getCreateAt()));
					row.createCell(8).setCellValue(follower.getNatureFollower().getName());
					rowNumber++;
				}

				/** COMMERCIAUX NE FAISONT PAS DES SUIVIS **/
				for (Commercial commercial : commercials) {
					long count = followersByDay.get(key).stream()
							.filter(f -> f.getCommercial().getId() == commercial.getId()).count();
					if (count == 0) {
						row = sheet.createRow(rowNumber);
						row.createCell(0).setCellValue(commercial.getName());
						this.createDangerCelllStyle(row.getCell(0));
						rowNumber++;
					}
				}

				sheetNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** SUIVI DES PROJET PAR COMMERCIAL **/
	public Workbook exportFollowedProjectsByCommercial() {
		try {

			/** Vérification des dates **/
			this.validateReportDates();

			/** Charge infos **/
			List<FollowerProject> followers = followerProjectRepository.findByCreateAtBetween(exportDto.getStartDate(),
					exportDto.getEndDate());

			if (followers.size() == 0) {
				return null;
			}

			List<FollowerProjectInfoDto> followersInfoDto = Utils.map(modelMapper, followers,
					FollowerProjectInfoDto.class);

			/** suivis par commercial **/
			Map<String, List<FollowerProjectInfoDto>> followersByCommercial = followersInfoDto.stream()
					.collect(Collectors.groupingBy(f -> f.getCommercial().getName()));

			/** Workbook setting **/
			file = new ClassPathResource(pathReportExcel + "/followed-projects-by-commercial.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);

			/** Period **/
			String period = "Du: "
					+ dateAndHourFormat.format(new Date(this.exportDto.getStartDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(this.exportDto.getEndDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getEndDate())));

			/** Cloner les fueilles **/
			int sheetNumber = 0;
			List<String> keys = followersByCommercial.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER)
					.collect(Collectors.toList());

			for (String key : keys) {
				if (sheetNumber != 0) {
					sheet = workbook.cloneSheet(0);
				} else {
					sheet = workbook.getSheetAt(0);
				}
				workbook.setSheetName(sheetNumber, key);
				sheet.getRow(0).getCell(7).setCellValue(period);
				sheetNumber++;
			}

			/** Générer le rapport **/
			sheetNumber = 0;
			int rowNumber = 4;
			String addressAfterProcessing, strStart = null;
			
			for (String key : keys) {

				sheet = workbook.getSheetAt(sheetNumber);
				rowNumber = 4;

				List<FollowerProjectInfoDto> data = followersByCommercial.get(key).stream()
						.sorted(Comparator.comparing(FollowerProjectInfoDto::getCreateAt).reversed())
						.collect(Collectors.toList());

				
				/** Insérer **/
				for (FollowerProjectInfoDto follower : data) {
					row = sheet.createRow(rowNumber);
					row.createCell(0).setCellValue(follower.getProject().getName());
					row.createCell(1).setCellValue(follower.getProject().getTypeProject().getName());
					row.createCell(2).setCellValue(follower.getProject().getDescription());
					
					try {
						strStart = follower.getProject().getLocality().getAddress();
						int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
						addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
						row.createCell(3).setCellValue(addressAfterProcessing);
						}catch (Exception e) {
							if (strStart != null) {
								row.createCell(3).setCellValue(strStart);
							} 
							// TODO: handle exception
						}

					if (follower.getProject().getLocality().getLatitude() != 0
							|| follower.getProject().getLocality().getLongitude() != 0) {
						this.createHeperlinK(row.getCell(3),
								"http://maps.google.com/?q=" + follower.getProject().getLocality().getLatitude() + ","
										+ follower.getProject().getLocality().getLongitude() + "&z=10");
					}

					if (follower.getCustomer() != null) {
						row.createCell(4).setCellValue(follower.getCustomer().getName());
						row.createCell(5).setCellValue(follower.getCustomer().getTypeCustomer().getName());
					}
					row.createCell(6).setCellValue(this.dateAndHourFormat.format(new Date(
							follower.getCreateAt().getTime() + Utils.getTimeZoneOffSet(follower.getCreateAt()))));
					row.createCell(7).setCellValue(follower.getNatureFollower().getName());
					rowNumber++;
				}

				sheetNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** CLIENTS PAR COMMERCIAL **/
	public Workbook exportCustomersByCommercial() {
		try {

			/** Vérification des dates **/
			this.validateReportDates();

			/** Charge infos **/
			List<Customer> customers = this.customerRepository.finAllByCreateAt(exportDto.getStartDate(),
					exportDto.getEndDate());

			if (customers.size() == 0) {
				return null;
			}

			/** get all commercial **/
			List<Commercial> commercials = customers.stream().map(c -> {
				return c.getCommercials();
			}).flatMap(Set::stream).collect(Collectors.toSet()).stream().sorted(Commercial.NameComparator)
					.collect(Collectors.toList());

			/** Workbook setting **/
			file = new ClassPathResource(pathReportExcel + "/customers-by-commercial.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);

			/** Period **/
			String period = "Du: "
					+ dateAndHourFormat.format(new Date(this.exportDto.getStartDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getStartDate())))
					+ "\n Au: " + dateAndHourFormat.format(new Date(this.exportDto.getEndDate().getTime()
							+ Utils.getTimeZoneOffSet(this.exportDto.getEndDate())));

			/** Cloner les fueilles **/
			int sheetNumber = 0;

			for (Commercial commercial : commercials) {
				if (sheetNumber != 0) {
					sheet = workbook.cloneSheet(0);
				} else {
					sheet = workbook.getSheetAt(0);
				}
				workbook.setSheetName(sheetNumber, commercial.getName());
				sheet.getRow(0).getCell(11).setCellValue(period);
				sheetNumber++;
			}

			/** Générer le rapport **/
			sheetNumber = 0;
			int rowNumber = 4;
			
			String addressAfterProcessing, strStart = null;

			for (Commercial commercial : commercials) {

				sheet = workbook.getSheetAt(sheetNumber);
				rowNumber = 4;

				List<Customer> customersByCommercial = customers.stream()
						.filter(c -> c.getCommercials().contains(commercial))
						.sorted(Comparator.comparing(Customer::getCreateAt).reversed()).collect(Collectors.toList());

				for (Customer customer : customersByCommercial) {
					row = sheet.createRow(rowNumber);
					row.createCell(0).setCellValue(customer.getName());
					row.createCell(1).setCellValue(customer.getTypeCustomer().getName());
					row.createCell(2).setCellValue(customer.getCode());
					
					try {
						strStart = customer.getLocality().getAddress();
						int i = strStart.indexOf(',', 1 + strStart.indexOf(',', 1 + strStart.indexOf(',')));
						addressAfterProcessing = strStart.substring(0, i).replaceAll("[^\\x00-\\x7f]|\\?", "");
						row.createCell(3).setCellValue(addressAfterProcessing);
						}catch (Exception e) {
							if (strStart != null) {
								row.createCell(3).setCellValue(strStart);
							} 
							// TODO: handle exception
						}
					
					if (customer.getLocality().getLatitude() != 0 || customer.getLocality().getLongitude() != 0) {
						this.createHeperlinK(row.getCell(3),
								"http://maps.google.com/?q=" + customer.getLocality().getLatitude() + ","
										+ customer.getLocality().getLongitude() + "&z=10");
					}
					row.createCell(4).setCellValue(customer.getCin());
					row.createCell(5).setCellValue(customer.getPhone1());
					row.createCell(6).setCellValue(customer.getPhone2());
					row.createCell(7).setCellValue(customer.getFax());
					row.createCell(8).setCellValue(customer.getEmail());
					row.createCell(9).setCellValue(customer.getRib());
					row.createCell(10).setCellValue(customer.getPatent());
					row.createCell(11).setCellValue(this.dateAndHourFormat.format(new Date(
							customer.getCreateAt().getTime() + Utils.getTimeZoneOffSet(customer.getCreateAt()))));

					rowNumber++;
				}

				sheetNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ================================= MODELES
	 * ====================================
	 **/

	/** TEMPLATE ARTICLE **/
	public Workbook exportArticleTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/articles.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(1);

			int rowNumber = 4;

			/**
			 * Ajouter les catégorie
			 */
			List<Category> categories = categoryRepository.findAll();

			for (Category category : categories) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(category.getName());
				row.createCell(1).setCellValue(category.getUnit());
				row.createCell(2).setCellValue(category.getObservation());

				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ByteArrayInputStream customerAccountParReference(Integer customerAccountId) throws IOException {

		CustomerAccount customerAccount = customerAccountRepository.getOne(customerAccountId);
		if (customerAccount == null)
			return null;
		CustomerAccountInfoDto customerAccountInfo = this.modelMapper.map(customerAccount,
				CustomerAccountInfoDto.class);

		try {
			file = new ClassPathResource(pathTemplateHtml + "/template-demande-client.html").getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		itextpdfPlugin itexPDF = new itextpdfPlugin();
		itexPDF.setFileDir(fileDir);
		return itexPDF.customerAccountParReference(customerAccountInfo, file, pathTemplateHtml);
	}

	public Workbook exportTiersTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/tiers.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);

			/***
			 * Style
			 */
			DataFormat format = workbook.createDataFormat();
			CellStyle phoneStyle = workbook.createCellStyle();
			phoneStyle.setDataFormat(format.getFormat("00 00 00 00 00"));

			/**
			 * Génération
			 * 
			 * /** ajouter les types des tiers
			 */
			int rowNumber = 4;

			sheet = workbook.getSheetAt(1);

			List<TypeTiers> typesTiers = typeTiersRepository.findAll();

			for (TypeTiers typeTiers : typesTiers) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(typeTiers.getName());
				row.createCell(1).setCellValue(typeTiers.getAbbreviation());
				row.createCell(2).setCellValue(typeTiers.getObservation());

				rowNumber++;
			}

			/**
			 * ajouter les types des clients
			 */

			rowNumber = 4;

			sheet = workbook.getSheetAt(2);

			List<TypeCustomer> typesCustomer = typeCustomerRepository.findAll();

			for (TypeCustomer typeCustomer : typesCustomer) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(typeCustomer.getName());
				row.createCell(1).setCellValue(typeCustomer.getAbbreviation());
				row.createCell(2).setCellValue(typeCustomer.getObservation());

				rowNumber++;
			}

			/**
			 * Ajouter les commerciaux
			 */
			rowNumber = 4;

			sheet = workbook.getSheetAt(3);

			List<Commercial> commercials = commercialRepository.findAll();

			for (Commercial commercial : commercials) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(commercial.getName());
				row.createCell(1).setCellValue(commercial.getAddress());
				row.createCell(2).setCellValue(commercial.getEmail());
				if (commercial.getPhone() != null && commercial.getPhone().trim().length() > 0) {
					row.createCell(3).setCellValue(Long.parseLong(commercial.getPhone()));
					row.getCell(3).setCellStyle(phoneStyle);
				}

				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** TEMPLATE Etat **/
	public Workbook exportStatesTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/states.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** TEMPLATE Projet **/
	public Workbook exportProjectTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/projects.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** TEMPLATE LOTISSEMENT **/
	private Workbook exportLotissementTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/lotissements.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** TEMPLATE Point de vente **/
	private Workbook exportPointOfSalesTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/points_de_vente.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/** TEMPLATE visiteur d'evenement **/
	public Workbook exportVisitorsEventTemplate() {
		try {

			/**
			 * Récupération de rapport
			 */
			file = new ClassPathResource(pathTemplateExcel + "/visiteurs.xlsx").getFile();
			fileInput = new FileInputStream(file);
			workbook = WorkbookFactory.create(fileInput);
			sheet = workbook.getSheetAt(0);

			String startPeriod = "Du: "
					+ dateAndHourFormat.format(new Date(
					exportDto.getStartDate().getTime()));

			String endPeriod = "Au: " + dateAndHourFormat.format(new Date(
					exportDto.getEndDate().getTime() ));



			sheet.getRow(1).getCell(10).setCellValue(startPeriod);
			sheet.getRow(2).getCell(10).setCellValue(endPeriod);

			int rowNumber = 4;

			/**
			 * Ajouter les catégorie
			 */
			List<EventTiers> eventTiers = eventTiersRepository.findByEventIdAndDate(this.exportDto.getEventId(), this.exportDto.getStartDate(), this.exportDto.getEndDate());
			String eventName = "Event: "+ eventTiers.get(0).getEvent().getName();
			sheet.getRow(0).getCell(10).setCellValue(eventName);
			for (EventTiers eventTier : eventTiers) {

				row = sheet.createRow(rowNumber);

				row.createCell(0).setCellValue(eventTier.getTiers().getName());
				row.createCell(1).setCellValue(eventTier.getEtablissement());
				row.createCell(2).setCellValue(eventTier.getTypeEtablissement());
				row.createCell(3).setCellValue(eventTier.getTypeTiers());
				row.createCell(4).setCellValue(eventTier.getVille());
				row.createCell(5).setCellValue(eventTier.getFonction());
				row.createCell(6).setCellValue(eventTier.getTiers().getPhone1());
				row.createCell(7).setCellValue(eventTier.getTiers().getEmail());
				if(!eventTier.getTiers().getCommercials().isEmpty()){
					List<String> commercialsName = eventTier.getTiers().getCommercials().stream().map(c -> c.getName())
							.collect(Collectors.toList());
					row.createCell(8).setCellValue(String.join(", ", commercialsName));
				}
				row.createCell(9).setCellValue(eventTier.getSample());
				row.createCell(10).setCellValue(eventTier.getObservation());
				row.createCell(11).setCellValue(dateAndHourFormat.format(new Date(
						eventTier.getTiers().getCreateAt().getTime() )));


				rowNumber++;
			}

			this.autoSizeColumns();
			return workbook;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ================================= FONCTIONS
	 * ====================================
	 **/

	/** VERIFICATION DES DATES **/
	private boolean validateReportDates() {

		if (this.exportDto.getStartDate() == null || this.exportDto.getEndDate() == null) {
			throw new ResourceNotFoundException("Période de rapport non trouvé !");
		}

		if (this.exportDto.getStartDate().compareTo(this.exportDto.getEndDate()) == 1) {
			throw new ResourceConflictException("Période de rapport incorrecte !");
		}

		return true;
	}

	public void autoSizeColumns() {
		int numberOfSheets = this.workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = this.workbook.getSheetAt(i);
			int numberOfRows = sheet.getPhysicalNumberOfRows();
			int lastCellNum = sheet.getRow(0).getLastCellNum();
			for (int j = 4; j < numberOfRows; j++) {
				Row row = sheet.getRow(j);
				if (row == null)
					continue;
				for (int k = 1; k < (lastCellNum - 1); k++) {
					Cell cell = row.getCell(k);
					if (cell == null)
						continue;
					sheet.autoSizeColumn(cell.getColumnIndex());
				}
			}
		}
	}

}
