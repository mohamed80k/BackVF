package com.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dto.ArticleAddDto;
import com.dto.CustomerAddDto;
import com.dto.ImportResponseDto;
import com.dto.LocalityDto;
import com.dto.LotissementAddDto;
import com.dto.ProjectAddDto;
import com.dto.StateProjectAddDto;
import com.dto.TiersAddDto;
import com.entity.Article;
import com.entity.Category;
import com.entity.Commercial;
import com.entity.Customer;
import com.entity.Locality;
import com.entity.Lotissement;
import com.entity.Project;
import com.entity.Site;
import com.entity.Society;
import com.entity.StateProject;
import com.entity.Tiers;
import com.entity.TypeCustomer;
import com.entity.TypeProject;
import com.entity.TypeTiers;
import com.entity.type.DasProject;
import com.entity.type.ImportType;
import com.entity.type.SegmentProject;
import com.entity.type.StatusProject;
import com.exception.ResourceNotFoundException;
import com.repository.ArticleRepository;
import com.repository.CategoryRepository;
import com.repository.CommercialRepository;
import com.repository.LocalityRepository;
import com.repository.LotissementRepository;
import com.repository.ProjectRepository;
import com.repository.SiteRepository;
import com.repository.SocietyRepository;
import com.repository.StateProjectRepository;
import com.repository.TiersRepository;
import com.repository.TypeCustomerRepository;
import com.repository.TypeProjectRepository;
import com.repository.TypeTiersRepository;
import com.service.ImportService;
import com.util.Utils;

@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	SocietyRepository societyRepository;

	@Autowired
	TiersRepository<Tiers> tiersRepository;

	@Autowired
	TiersRepository<Customer> customerRepository;

	@Autowired
	TypeTiersRepository typeTiersRepository;

	@Autowired
	LocalityRepository localityRepository;

	@Autowired
	TypeCustomerRepository typeCustomerRepository;

	@Autowired
	CommercialRepository commercialRepository;

	@Autowired
	StateProjectRepository stateProjectRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	TypeProjectRepository typeProjectRepository;

	@Autowired
	LotissementRepository lotissementRepository;

	@Autowired
	SiteRepository siteRepository;

//	@Autowired
//	PointOfSaleRepository pointOfSaleRepository;

	ModelMapper modelMapper = new ModelMapper();

	private MultipartFile file;
	private Integer societyId;

	@Override
	public List<ImportResponseDto> importFile(ImportType importType, MultipartFile file, Integer societyId) {

		this.file = file;
		this.societyId = societyId;

		switch (importType) {
		case articles:
			return importArticles();
		case tiers:
			return importTiers();
		case states:
			return importStates();
		case projects:
			return importProjects();
		case lotissements:
			return importLotissements();
//		case pointOfSales:
//			return importPointOfSales();
		default:
			throw new ResourceNotFoundException("Type d'importe non trouvé !");
		}

	}

	/**
	 * Articles
	 */
	@Transactional
	public List<ImportResponseDto> importArticles() {

		/**
		 * ImportResponseDto
		 */
		List<ImportResponseDto> importResponses = new ArrayList<>();

		String responseTitle = null;

		try {

			/**
			 * Vérification de society
			 */
			Society society = societyRepository.getOne(this.societyId);
			if (society == null) {
				throw new ResourceNotFoundException("Societé non trouvé !");
			}

			/**
			 * Obtenir fichier à importer
			 */
			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			/**
			 * article
			 */
			Article article = new Article();

			/**
			 * ArticleAddDto
			 */
			ArticleAddDto articleAdd = new ArticleAddDto();

			/**
			 * categorie
			 */
			Category category = new Category();

			/**
			 * Debut
			 */
			int rowNumber = 4;

			for (Row row : sheet) {

				if (row.getRowNum() < rowNumber) {
					continue;
				}

				/**
				 * reference
				 */
				Cell referenceCell = row.getCell(0);
				if (referenceCell == null) {
					importResponses.add(new ImportResponseDto(false, "La référence est null !", null));
					continue;
				} else {
					responseTitle = "Article «" + referenceCell.getStringCellValue() + "»";
					/**
					 * Vérification de reference
					 */
					if (articleRepository.existsByReference(referenceCell.getStringCellValue())) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Existe déja !"));
						continue;
					} else {
						articleAdd.setReference(referenceCell.getStringCellValue());
					}
				}

				/**
				 * designation
				 */
				Cell designationCell = row.getCell(1);
				if (designationCell != null) {
					articleAdd.setDesignation(designationCell.getStringCellValue());
				}

				/**
				 * price
				 */
				Cell priceCell = row.getCell(2);
				if (priceCell != null && priceCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					articleAdd.setPrice(priceCell.getNumericCellValue());
				}

				/**
				 * length
				 */
				Cell lengthCell = row.getCell(3);
				if (lengthCell != null && lengthCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					articleAdd.setLength(lengthCell.getNumericCellValue());
				}

				/**
				 * weight
				 */
				Cell weightCell = row.getCell(4);
				if (weightCell != null && weightCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					articleAdd.setWeight(weightCell.getNumericCellValue());
				}

				/**
				 * unit
				 */
				Cell unitCell = row.getCell(5);
				if (unitCell != null) {
					articleAdd.setUnit(unitCell.getStringCellValue());
				}

				/**
				 * category
				 */
				Cell categoryCell = row.getCell(6);
				if (categoryCell == null) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "La catégorie est null !"));
					continue;
				} else {
					/**
					 * Rechercher catégorie
					 */
					category = categoryRepository.findByName(categoryCell.getStringCellValue());

					if (category == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Catégorie non trouvé !"));
						continue;
					} else if (category.getSociety().getId() != this.societyId) {
						importResponses.add(new ImportResponseDto(false, responseTitle,
								"La catégorie «" + categoryCell.getStringCellValue()
										+ "» n'appartient pas à la societé «" + society.getName() + "» !"));
						continue;
					}

					articleAdd.setCategory(category.getId());
				}

				/**
				 * Validation de dto article
				 */
				List<String> articleErrors = Utils.validateDto(articleAdd);
				if (articleErrors.size() > 0) {
					for (String error : articleErrors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				}

				/**
				 * Enregistrer
				 */
				article = modelMapper.map(articleAdd, Article.class);
				article.setCategory(category);

				articleRepository.save(article);

				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return importResponses;

	}

	/***
	 * Projets
	 */
	@Transactional
	public List<ImportResponseDto> importProjects() {

		/**
		 * ImportResponseDto
		 */
		List<ImportResponseDto> importResponses = new ArrayList<>();

		String responseTitle = null;

		try {

			/**
			 * Obtenir fichier à importer
			 */
			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			/**
			 * Project
			 */
			Project project = new Project();

			/**
			 * ProjectAddDto
			 */
			ProjectAddDto projectAddDto = new ProjectAddDto();

			/**
			 * TypeProject
			 */
			TypeProject typeProject = new TypeProject();

			/**
			 * Tiers
			 */
			Set<Tiers> tiers = new HashSet<>();

			Tiers tier = new Tiers();

			/**
			 * Locality
			 */
			Locality locality = new Locality();

			/**
			 * LocalityDto
			 */
			LocalityDto localityDto = new LocalityDto();

			/**
			 * Liste de commerciax
			 */
			Set<Commercial> commercials = new HashSet<>();

			Commercial commercial = new Commercial();

			/**
			 * Liste de das
			 */
			Set<DasProject> das = new HashSet<DasProject>();

			/**
			 * Site
			 */
			Site siteProject = new Site();

			/**
			 * State Project
			 */
			StateProject stateProject = new StateProject();

			Society society = societyRepository.getOne(1);

			/**
			 * liste des erreurs
			 */
			List<String> errors = new ArrayList<>();

			/**
			 * Début
			 */
			int rowNumber = 4;

			for (Row row : sheet) {

				if (row.getRowNum() < rowNumber) {
					continue;
				}
				
				/**	
				 * Nom
				 */
				Cell nameCell = row.getCell(0);

				if (nameCell == null || nameCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, "Le nom est null !", null));
					continue;
				} else {
					responseTitle = "«" + nameCell.getStringCellValue() + "»";
					/**
					 * Vérification de nom
					 */
					if (projectRepository.existsByName(nameCell.getStringCellValue())) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Existe déja !"));
						continue;
					} else {
						projectAddDto.setName(nameCell.getStringCellValue());
					}
				}

				/**
				 * type
				 */
				Cell typeCell = row.getCell(1);

				if (typeCell == null || typeCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "Type est null !"));
					continue;
				} else {
					typeProject = typeProjectRepository.findByName(typeCell.getStringCellValue());
					if (typeProject == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Type de projet non trouvé !"));
						continue;
					} else {
						projectAddDto.setTypeProject(typeProject.getId());
					}
				}

				/**
				 * Loalité
				 */
				localityDto = new LocalityDto();

				/**
				 * Adresse
				 */
				Cell addressCell = row.getCell(2);

				if (addressCell != null && addressCell.getCellType() == Cell.CELL_TYPE_STRING) {
					localityDto.setAddress(addressCell.getStringCellValue());
				}

				/**
				 * Latitude
				 */
				Cell latitudeCell = row.getCell(3);

				if (latitudeCell != null && latitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLatitude(latitudeCell.getNumericCellValue());
				}

				/**
				 * Longitude
				 */
				Cell longitudeCell = row.getCell(4);

				if (longitudeCell != null && longitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLongitude(longitudeCell.getNumericCellValue());
				}

				/**
				 * Nom de locality
				 */
				localityDto.setName(projectAddDto.getName());

				/**
				 * Description
				 */
				Cell descriptionCell = row.getCell(5);
				if (descriptionCell != null && descriptionCell.getCellType() == Cell.CELL_TYPE_STRING) {
					projectAddDto.setDescription(descriptionCell.getStringCellValue());
				}

				/**
				 * Client
				 */
				tiers = new HashSet<Tiers>();
				Cell clientCell = row.getCell(6);
				if (clientCell != null && clientCell.getCellType() == Cell.CELL_TYPE_STRING) {
					String[] clients = clientCell.getStringCellValue().split(",");
					for (int i = 0; i < clients.length; i++) {
						tier = tiersRepository.findByNameAndTypes(clients[i], java.util.Arrays.asList("Client"));
						if (tier != null) {
							tiers.add(tier);
						}
					}
				}
				
				/**
				 * Segment
				 */
				Cell segmentCell = row.getCell(8);
				SegmentProject segment = null;
				if (segmentCell != null && segmentCell.getCellType() == Cell.CELL_TYPE_STRING) {
					switch (segmentCell.getStringCellValue().toLowerCase()) {
					case "entreprise":
						segment = SegmentProject.Entreprise;
						break;
					case "particulier":
						segment = SegmentProject.Particulier;
						break;
					case "revendeur":
						segment = SegmentProject.Revendeur;
						break;
					default:
						importResponses.add(new ImportResponseDto(false, responseTitle, "Segment Non trouvé !"));
						continue;
					}
					projectAddDto.setSegment(segment);
				}

				/**
				 * Commerciaux
				 */
				commercials = new HashSet<Commercial>();
				String[] commercialsCell = null;
				Cell commercialCell = row.getCell(9);
				if (commercialCell != null && commercialCell.getCellType() == Cell.CELL_TYPE_STRING) {
					commercialsCell = commercialCell.getStringCellValue().split(",");
					for (int j = 0; j < commercialsCell.length; j++) {
						commercial = new Commercial();
						commercial = commercialRepository.findByName(commercialsCell[j]);
						if (commercial != null) {
							commercials.add(commercial);
						}
					}
				}

				/**
				 * Status
				 */
				Cell statusCell = row.getCell(10);
				StatusProject status = null;

				if (statusCell != null && statusCell.getCellType() == Cell.CELL_TYPE_STRING) {
					switch (statusCell.getStringCellValue().toLowerCase()) {
					case "menara":
						status = StatusProject.Menara;
						break;
					case "concurrent":
						importResponses.add(new ImportResponseDto(false, responseTitle, "Statut concurrent n'est pas pris en charge !"));
						continue;
					case "conclus":
						status = StatusProject.Conclus;
						break;
					case "en prospection":
					case "en_prospection":
						status = StatusProject.En_Prospection;
						break;
					case "partage":
					case "partagé":
						status = StatusProject.Partage;
						break;
					default:
						importResponses.add(new ImportResponseDto(false, responseTitle, "Statut Non trouvé !"));
						continue;
					}
					projectAddDto.setStatus(status);
				} else {
					importResponses.add(new ImportResponseDto(false, responseTitle, "Statut Non trouvé !"));
					continue;
				}

				/**
				 * etat
				 */
				Cell statesCell = row.getCell(11);

				if (statesCell == null || statesCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "L'état est null !"));
					continue;
				} else {
					stateProject = stateProjectRepository.findByName(statesCell.getStringCellValue());
					if (stateProject == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "état de projet non trouvé !"));
						continue;
					} else {
						projectAddDto.setStateProject(stateProject.getId());
					}
				}
				
				/**
				 * site
				 */
				Cell siteCell = row.getCell(12);

				if (siteCell == null || siteCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "site est null !"));
					continue;
				} else {
					siteProject = siteRepository.findBySocietyAndDesignation(society.getId(),
							siteCell.getStringCellValue());
					if (siteProject == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "site non trouvé !"));
						continue;
					} else {
						projectAddDto.setSiteProject(siteProject.getId());
					}
				}
				
				/**
				 * DAS
				 */
				Cell dasCell = row.getCell(13);

				das.clear();
				String[] multiDasCell = null;
				if (dasCell != null && dasCell.getCellType() == Cell.CELL_TYPE_STRING) {
					multiDasCell = dasCell.getStringCellValue().split(",");
					for (int i = 0; i < multiDasCell.length; i++) {
					switch (multiDasCell[i].toLowerCase()) {
					case "aménagement":
					case "amenagement":
						das.add(DasProject.Amenagement);
						break;
					case "structure":
						das.add(DasProject.Structure);
						break;
					default:
						importResponses.add(new ImportResponseDto(false, responseTitle, "das Non trouvé !"));
						continue;
					}
					
					}
					if (!das.isEmpty()) {
						projectAddDto.setDas(das);
					}
				}else {
					importResponses.add(new ImportResponseDto(false, responseTitle, "DAS est null !"));
					continue;
				}
				

				/**
				 * lotissement
				 */
				Cell lotissementCell = row.getCell(14);
				Lotissement lotissement = null;
				if (lotissementCell != null && lotissementCell.getCellType() == Cell.CELL_TYPE_STRING) {
					responseTitle = lotissementCell.getStringCellValue() + " " + responseTitle;
					lotissement = lotissementRepository.findByName(lotissementCell.getStringCellValue());
					if (lotissement != null) {
						projectAddDto.setProjectLotissement(lotissement.getId());
					}
				}
				

				/**
				 * C.A Estimé
				 */
				Cell caEstimeCell = row.getCell(15);
				if (caEstimeCell != null) {
					if (caEstimeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						projectAddDto.setCaEstime(caEstimeCell.getNumericCellValue());
					} else if (caEstimeCell.getCellType() == Cell.CELL_TYPE_STRING) {
						projectAddDto.setCaEstime(Double.parseDouble(
								caEstimeCell.getStringCellValue().replaceAll("\\u00A0", "").replace(",", ".")));
					}
				}else {
					importResponses.add(new ImportResponseDto(false, responseTitle, "C.A Estimé est null !"));
					continue;
				}

				
				/** set date creation **/
				Cell dateCell = row.getCell(18);
				Date createAt = null;
				if (dateCell != null) {
					if (dateCell.getCellType() == Cell.CELL_TYPE_STRING) {
						createAt = new SimpleDateFormat("dd-MM-yyyy, HH:mm").parse(dateCell.getStringCellValue());
					} else {
						createAt = dateCell.getDateCellValue();

					}
					projectAddDto.setCreateAt(createAt);
				}
				
				/** Vérification des dates **/
				Date date = new Date();

				if (projectAddDto.getCreateAt() == null) {
					projectAddDto.setCreateAt(date);
				} else if (projectAddDto.getCreateAt().compareTo(date) == 1) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "Date création du projet incorrect !"));
					continue;
				}
				

				/**
				 * Validation de dto locality
				 */
				errors = Utils.validateDto(localityDto);
				if (errors.size() > 0) {
					for (String error : errors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				} else {
					locality = modelMapper.map(localityDto, Locality.class);
					locality.setId(null);
					localityRepository.save(locality);
					projectAddDto.setLocality(localityDto);
				}

				/**
				 * Validation de dto project
				 */
				List<String> projectErrors = Utils.validateDto(projectAddDto);
				if (projectErrors.size() > 0) {
					for (String error : projectErrors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				}

				/**
				 * Enregistrer
				 */

				project = modelMapper.map(projectAddDto, Project.class);
				project.setId(null);
				project.setLocality(locality);
				project.setCommercials(commercials);
				project.setTiers(tiers);
				project.setTypeProject(typeProject);
				project.setStateProject(stateProject);
				project.setSiteProject(siteProject);
				project.setProjectLotissement(lotissement);
				/**
				 * Date de création
				 */

				if (project.getStateProject().getName().equals("CLOTURE")) {
					project.setClosedAt(date);
				}

				projectRepository.save(project);
				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return importResponses;
	}

	/**
	 * States
	 */
	@Transactional
	public List<ImportResponseDto> importStates() {

		/**
		 * ImportResponseDto
		 */
		List<ImportResponseDto> importResponses = new ArrayList<>();

		String responseTitle = null;

		try {
			/**
			 * Obtenir fichier à importer
			 */
			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			/**
			 * StateProject
			 */
			StateProject stateProject = new StateProject();

			/**
			 * stateProjectAddDto
			 */
			StateProjectAddDto stateProjectAddDto = new StateProjectAddDto();

			/**
			 * Debut
			 */
			int rowNumber = 4;

			for (Row row : sheet) {

				if (row.getRowNum() < rowNumber) {
					continue;
				}

				/**
				 * Nom
				 */
				Cell nomCell = row.getCell(0);
				if (nomCell == null) {
					importResponses.add(new ImportResponseDto(false, "Le Nom est null !", null));
					continue;
				} else {
					stateProjectAddDto.setName(nomCell.getStringCellValue());
				}

				/**
				 * abbreviation
				 */
				Cell abbreviationCell = row.getCell(1);
				if (abbreviationCell != null) {
					stateProjectAddDto.setAbbreviation(abbreviationCell.getStringCellValue());
				}

				/**
				 * observation
				 */
				Cell observationCell = row.getCell(2);
				if (observationCell != null) {
					stateProjectAddDto.setObservation(observationCell.getStringCellValue());
				}

				/**
				 * Validation de dto statesProject
				 */
				List<String> statesErrors = Utils.validateDto(stateProjectAddDto);
				if (statesErrors.size() > 0) {
					for (String error : statesErrors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				}

				/**
				 * Enregistrer
				 */
				stateProject = modelMapper.map(stateProjectAddDto, StateProject.class);

				stateProjectRepository.save(stateProject);

				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return importResponses;

	}

	/**
	 * Lotissement
	 */
	@Transactional
	public List<ImportResponseDto> importLotissements() {

		/**
		 * ImportResponseDto
		 */
		List<ImportResponseDto> importResponses = new ArrayList<>();

		String responseTitle = null;

		try {
			/**
			 * Obtenir fichier à importer
			 */
			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			/**
			 * lotissement
			 */
			Lotissement lotissement = new Lotissement();
			/**
			 * lotissementAddDto
			 */
			LotissementAddDto lotissementAddDto = new LotissementAddDto();
			/**
			 * TypeProject
			 */
			TypeProject typeProject = new TypeProject();
			/**
			 * Locality
			 */
			Locality locality = new Locality();
			/**
			 * LocalityDto
			 */
			LocalityDto localityDto = new LocalityDto();
			/**
			 * Tiers
			 */
			Set<Tiers> tiers = new HashSet<>();
			Tiers tier = new Tiers();
			/**
			 * liste des erreurs
			 */
			List<String> errors = new ArrayList<>();
			/**
			 * Debut
			 */
			int rowNumber = 4;

			for (Row row : sheet) {

				if (row.getRowNum() < rowNumber) {
					continue;
				}

				/**
				 * Nom
				 */
				Cell nomCell = row.getCell(0);
				if (nomCell == null) {
					importResponses.add(new ImportResponseDto(false, "Le Nom est null !", null));
					continue;
				} else {
					lotissementAddDto.setName(nomCell.getStringCellValue());
				}

				/**
				 * Loalité
				 */
				localityDto = new LocalityDto();

				/**
				 * Adresse
				 */
				Cell addressCell = row.getCell(1);

				if (addressCell != null && addressCell.getCellType() == Cell.CELL_TYPE_STRING) {
					localityDto.setAddress(addressCell.getStringCellValue());
				}

				/**
				 * Latitude
				 */
				Cell latitudeCell = row.getCell(2);

				if (latitudeCell != null && latitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLatitude(latitudeCell.getNumericCellValue());
				}

				/**
				 * Longitude
				 */
				Cell longitudeCell = row.getCell(3);

				if (longitudeCell != null && longitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLongitude(longitudeCell.getNumericCellValue());
				}

				/**
				 * Nom de locality
				 */
				localityDto.setName(lotissementAddDto.getName());

				/**
				 * Validation de dto locality
				 */
				errors = Utils.validateDto(localityDto);
				if (errors.size() > 0) {
					for (String error : errors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				} else {
					locality = modelMapper.map(localityDto, Locality.class);
					locality.setId(null);
					localityRepository.save(locality);
					lotissementAddDto.setLocality(localityDto);
				}

				/**
				 * Description
				 */
				Cell descriptionCell = row.getCell(4);
				if (descriptionCell != null) {
					lotissementAddDto.setDescription(descriptionCell.getStringCellValue());
				}

				/**
				 * Type
				 */
				Cell typeCell = row.getCell(5);

				if (typeCell == null || typeCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "Type est null !"));
					continue;
				} else {
					typeProject = typeProjectRepository.findByName(typeCell.getStringCellValue());
					if (typeProject == null) {
						importResponses
								.add(new ImportResponseDto(false, responseTitle, "Type de lotissement non trouvé !"));
						continue;
					} else {
						lotissementAddDto.setTypeLotissement(typeProject.getId());
					}
				}

				/**
				 * Surface
				 */
				Cell surfaceCell = row.getCell(6);
				if (surfaceCell != null) {
					if (surfaceCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						lotissementAddDto.setSurface(String.valueOf(surfaceCell.getNumericCellValue()));
					} else if (surfaceCell.getCellType() == Cell.CELL_TYPE_STRING) {
						lotissementAddDto.setSurface(surfaceCell.getStringCellValue());
					}
				}

				/**
				 * Nombre total des lots
				 */
				Cell NbrTotalLotsCell = row.getCell(7);
				if (NbrTotalLotsCell != null) {
					if (NbrTotalLotsCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						lotissementAddDto.setNombreTotalLot((int) NbrTotalLotsCell.getNumericCellValue());
					} else if (NbrTotalLotsCell.getCellType() == Cell.CELL_TYPE_STRING) {
						lotissementAddDto.setNombreTotalLot(Integer.parseInt(
								NbrTotalLotsCell.getStringCellValue().replaceAll("\\u00A0", "").replace(",", ".")));
					}
				}

				/**
				 * Maitres d'ouvrage
				 */

				tiers.clear();
				Cell MaitresOuvrageCell = row.getCell(8);
				if (MaitresOuvrageCell != null && MaitresOuvrageCell.getCellType() == Cell.CELL_TYPE_STRING) {
					tier = tiersRepository.findByNameAndTypes(MaitresOuvrageCell.getStringCellValue(),
							java.util.Arrays.asList("Maitre d'oeuvre"));
					if (tier != null) {
						tiers.add(tier);
					}
				}

				/**
				 * Validation de dto Lotissement
				 */
				List<String> statesErrors = Utils.validateDto(lotissementAddDto);
				if (statesErrors.size() > 0) {
					for (String error : statesErrors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				}

				/**
				 * Enregistrer
				 */

				lotissement = modelMapper.map(lotissementAddDto, Lotissement.class);
				lotissement.setId(null);
				lotissement.setTiers(tiers);
				lotissement.setTypeLotissement(typeProject);
				lotissementRepository.save(lotissement);

				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return importResponses;

	}

	/**
	 * Point de vente
	 */
//	@Transactional
//	public List<ImportResponseDto> importPointOfSales() {
//
//		/**
//		 * ImportResponseDto
//		 */
//		List<ImportResponseDto> importResponses = new ArrayList<>();
//
//		String responseTitle = null;
//
//		try {
//			/**
//			 * Obtenir fichier à importer
//			 */
//			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
//			Sheet sheet = workbook.getSheetAt(0);
//			/**
//			 * point de vente
//			 */
//			PointOfSale pointOfSale = new PointOfSale();
//			/**
//			 * pointOfSaleAddDto
//			 */
//			PointOfSaleAddDto pointOfSaleAddDto = new PointOfSaleAddDto();
//			/**
//			 * Site
//			 */
//			Site site = new Site();
//			/**
//			 * Article
//			 */
//			Article article = new Article();
//			/**
//			 * Articles
//			 */
//			Set<Article> articles = new HashSet<>();
//			/**
//			 * Category
//			 */
//			Category category = new Category();
//			/**
//			 * Society
//			 */
//			Society society = new Society();
//			/**
//			 * Locality
//			 */
//			Locality locality = new Locality();
//			/**
//			 * LocalityDto
//			 */
//			LocalityDto localityDto = new LocalityDto();
//			/**
//			 * Tiers
//			 */
//			Set<Tiers> tiers = new HashSet<>();
//			Tiers tier = new Tiers();
//			/**
//			 * liste des erreurs
//			 */
//			List<String> errors = new ArrayList<>();
//			/**
//			 * Debut
//			 */
//			int rowNumber = 4;
//
//			for (Row row : sheet) {
//
//				if (row.getRowNum() < rowNumber) {
//					continue;
//				}
//
//				/**
//				 * Nom
//				 */
//				Cell nomCell = row.getCell(0);
//				if (nomCell == null) {
//					importResponses.add(new ImportResponseDto(false, "Le Nom est null !", null));
//					continue;
//				} else {
//					pointOfSaleAddDto.setName(nomCell.getStringCellValue());
//				}
//
//				/**
//				 * Loalité
//				 */
//				localityDto = new LocalityDto();
//
//				/**
//				 * Adresse
//				 */
//				Cell addressCell = row.getCell(1);
//
//				if (addressCell != null && addressCell.getCellType() == Cell.CELL_TYPE_STRING) {
//					localityDto.setAddress(addressCell.getStringCellValue());
//				}
//
//				/**
//				 * Latitude
//				 */
//				Cell latitudeCell = row.getCell(2);
//
//				if (latitudeCell != null && latitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//					localityDto.setLatitude(latitudeCell.getNumericCellValue());
//				}
//
//				/**
//				 * Longitude
//				 */
//				Cell longitudeCell = row.getCell(3);
//
//				if (longitudeCell != null && longitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//					localityDto.setLongitude(longitudeCell.getNumericCellValue());
//				}
//
//				/**
//				 * Nom de locality
//				 */
//				localityDto.setName(pointOfSaleAddDto.getName());
//
//				/**
//				 * Validation de dto locality
//				 */
//				errors = Utils.validateDto(localityDto);
//				if (errors.size() > 0) {
//					for (String error : errors) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, error));
//					}
//					continue;
//				} else {
//					locality = modelMapper.map(localityDto, Locality.class);
//					locality.setId(null);
//					localityRepository.save(locality);
//					pointOfSaleAddDto.setLocality(localityDto);
//				}
//
//				/**
//				 * Status
//				 */
//				Cell statusCell = row.getCell(4);
//				StatusProject status = null;
//
//				if (statusCell != null && statusCell.getCellType() == Cell.CELL_TYPE_STRING) {
//					switch (statusCell.getStringCellValue().toLowerCase()) {
//					case "menara":
//						status = StatusProject.Menara;
//						break;
//					case "concurrent":
//						status = StatusProject.Concurrent;
//						break;
//					case "conclus":
//						status = StatusProject.Conclus;
//						break;
//					case "en prospection":
//					case "en_prospection":
//						status = StatusProject.En_Prospection;
//						break;
//					case "partage":
//					case "partagé":
//						status = StatusProject.Partage;
//						break;
//					default:
//						importResponses.add(new ImportResponseDto(false, responseTitle, "Statut Non trouvé !"));
//						continue;
//					}
//					pointOfSaleAddDto.setStatus(status);
//				} else {
//					importResponses.add(new ImportResponseDto(false, responseTitle, "Statut Non trouvé !"));
//					continue;
//				}
//
//				/**
//				 * Surface
//				 */
//				Cell pointOfSaleCell = row.getCell(5);
//				if (pointOfSaleCell != null) {
//					if (pointOfSaleCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//						pointOfSaleAddDto.setSurface(String.valueOf(pointOfSaleCell.getNumericCellValue()));
//					} else if (pointOfSaleCell.getCellType() == Cell.CELL_TYPE_STRING) {
//						pointOfSaleAddDto.setSurface(pointOfSaleCell.getStringCellValue());
//					}
//				}
//
//				/**
//				 * societe
//				 */
//				Cell societyCell = row.getCell(6);
//				society = new Society();
//				if (societyCell == null || societyCell.getCellType() != Cell.CELL_TYPE_STRING) {
//					importResponses.add(new ImportResponseDto(false, responseTitle, "society est null !"));
//					continue;
//				} else {
//					society = societyRepository.findByName(societyCell.getStringCellValue());
//					if (society == null) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, "society est null !"));
//						continue;
//					}
//				}
//
//				/**
//				 * category
//				 */
//				Cell categoryCell = row.getCell(7);
//				category = new Category();
//				if (categoryCell == null) {
//					importResponses.add(new ImportResponseDto(false, responseTitle, "La catégorie est null !"));
//					continue;
//				} else {
//					/**
//					 * Rechercher catégorie
//					 */
//					category = categoryRepository.findByName(categoryCell.getStringCellValue());
//
//					if (category == null) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, "Catégorie non trouvé !"));
//						continue;
//					} else if (category.getSociety().getId() != society.getId()) {
//						importResponses.add(new ImportResponseDto(false, responseTitle,
//								"La catégorie «" + categoryCell.getStringCellValue()
//										+ "» n'appartient pas à la societé «" + society.getName() + "» !"));
//						continue;
//					}
//
//				}
//
//				/**
//				 * article
//				 */
//				Cell articleCell = row.getCell(8);
//				article = new Article();
//				articles = new HashSet<Article>();
//				if (articleCell == null) {
//					importResponses.add(new ImportResponseDto(false, responseTitle, "L'article est null !"));
//					continue;
//				} else {
//					/**
//					 * Rechercher article
//					 */
//					article = articleRepository.findByReference(articleCell.getStringCellValue());
//
//					if (article == null) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, "Article non trouvé !"));
//						continue;
//					} else if (article.getCategory().getId() != category.getId()) {
//						importResponses.add(new ImportResponseDto(false, responseTitle,
//								"L'article «" + articleCell.getStringCellValue() + "» n'appartient pas à la catégorie «"
//										+ category.getName() + "» !"));
//						continue;
//					} else {
//						articles.add(article);
//						pointOfSaleAddDto.setArticles(Arrays.asList(article.getId()));
//					}
//				}
//
//				/**
//				 * site
//				 */
//				Cell siteCell = row.getCell(9);
//				site = new Site();
//
//				if (siteCell == null || siteCell.getCellType() != Cell.CELL_TYPE_STRING) {
//					importResponses.add(new ImportResponseDto(false, responseTitle, "site est null !"));
//					continue;
//				} else {
//					site = siteRepository.findBySocietyAndDesignation(society.getId(), siteCell.getStringCellValue());
//					if (site == null) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, "site non trouvé !"));
//						continue;
//					} else {
//						pointOfSaleAddDto.setSite(site.getId());
//					}
//				}
//
//				/**
//				 * Revendeurs
//				 */
//				tiers = new HashSet<Tiers>();
//				Cell revendeursCell = row.getCell(10);
//				tier = new Tiers();
//				if (revendeursCell != null && revendeursCell.getCellType() == Cell.CELL_TYPE_STRING) {
//					tier = tiersRepository.findByNameAndTypes(revendeursCell.getStringCellValue(),
//							java.util.Arrays.asList("Client"));
//					if (tier != null) {
//						Set<Integer> tierId = new HashSet<Integer>();
//						tierId.add(tier.getId());
//						pointOfSaleAddDto.setTiers(tierId);
//						tiers.add(tier);
//					}
//				}
//
//				/**
//				 * Concurrent
//				 */
//
//				Cell ConcurrentCell = row.getCell(11);
//				tier = new Tiers();
//				if (ConcurrentCell != null && ConcurrentCell.getCellType() == Cell.CELL_TYPE_STRING
//						&& pointOfSaleAddDto.getStatus().name().equals("Concurrent")) {
//					tier = tiersRepository.findByNameAndTypes(ConcurrentCell.getStringCellValue(),
//							Arrays.asList("Concurrent"));
//					if (tier != null) {
//						Set<Integer> tierIds = new HashSet<Integer>();
//						tierIds.add(tier.getId());
//						pointOfSaleAddDto.setTiers(tierIds);
//						tiers.add(tier);
//					}
//				}
//
//				/**
//				 * Validation de dto pointOfSale
//				 */
//				List<String> statesErrors = Utils.validateDto(pointOfSaleAddDto);
//				if (statesErrors.size() > 0) {
//					for (String error : statesErrors) {
//						importResponses.add(new ImportResponseDto(false, responseTitle, error));
//					}
//					continue;
//				}
//
//				/**
//				 * Enregistrer
//				 */
//				modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//				pointOfSale = modelMapper.map(pointOfSaleAddDto, PointOfSale.class);
//				pointOfSale.setId(null);
//				pointOfSale.setLocality(locality);
//				pointOfSale.setTiers(tiers);
//				pointOfSale.setArticles(articles);
//				pointOfSale.setSite(site);
//
//				pointOfSaleRepository.save(pointOfSale);
//
//				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return importResponses;
//
//	}

	/***
	 * Tiers
	 */
	@Transactional
	public List<ImportResponseDto> importTiers() {

		/**
		 * ImportResponseDto
		 */
		List<ImportResponseDto> importResponses = new ArrayList<>();

		String responseTitle = null;

		try {

			/**
			 * Obtenir fichier à importer
			 */
			Workbook workbook = WorkbookFactory.create(this.file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);

			/**
			 * Tiers
			 */
			Tiers tiers = new Tiers();

			/**
			 * TiersAddDto
			 */
			TiersAddDto tiersAdd = new TiersAddDto();

			/**
			 * TypeTiers
			 */
			TypeTiers typeTiers = new TypeTiers();

			/**
			 * Customer
			 */
			Customer customer = new Customer();

			String customerCode = null;

			/**
			 * CustomerAddDto
			 */
			CustomerAddDto customerAdd = new CustomerAddDto();

			/**
			 * TypeCustomer
			 */
			TypeCustomer typeCustomer = new TypeCustomer();

			/**
			 * Locality
			 */
			Locality locality = new Locality();

			/**
			 * isCustomer
			 */
			boolean isCustomer = false;

			/**
			 * LocalityDto
			 */
			LocalityDto localityDto = new LocalityDto();

			/**
			 * Commercial
			 */
			Commercial commercial = new Commercial();

			/**
			 * Liste de commerciax
			 */
			Set<Commercial> commercials = new HashSet<>();

			/**
			 * liste des erreurs
			 */
			List<String> errors = new ArrayList<>();

			/**
			 * Format tél
			 */
			DecimalFormat phoneDecimalFormat = new DecimalFormat("0000000000");

			/**
			 * Début
			 */
			int rowNumber = 4;

			for (Row row : sheet) {

				if (row.getRowNum() < rowNumber) {
					continue;
				}

				/**
				 * Nom
				 */
				Cell nameCell = row.getCell(3);

				if (nameCell == null || nameCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, "Le nom est null !", null));
					continue;
				} else {
					responseTitle = "«" + nameCell.getStringCellValue() + "»";
					/**
					 * Vérification de nom
					 */
					if (tiersRepository.existsByName(nameCell.getStringCellValue())) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Existe déja !"));
						continue;
					} else {
						tiersAdd.setName(nameCell.getStringCellValue());
					}
				}

				/**
				 * Type de tiers
				 */
				Cell typeTiersCell = row.getCell(0);

				if (typeTiersCell == null || typeTiersCell.getCellType() != Cell.CELL_TYPE_STRING) {
					importResponses.add(new ImportResponseDto(false, responseTitle, "Le nom est null !"));
					continue;
				} else {
					responseTitle = typeTiersCell.getStringCellValue() + " " + responseTitle;
					typeTiers = typeTiersRepository.findByName(typeTiersCell.getStringCellValue());
					if (typeTiers == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Type de tiers non trouvé !"));
						continue;
					} else {
						tiersAdd.setTypeTiers(typeTiers.getId());
					}
				}

				/**
				 * vérification de type
				 */
				if (typeTiers.getName().equals("Client")) {
					isCustomer = true;
				} else {
					isCustomer = false;
				}

				/**
				 * Si le tiers est de type client
				 */
				if (isCustomer) {
					/**
					 * Type de client
					 */
					Cell typeCustomerCell = row.getCell(1);
					if (typeCustomerCell == null || typeCustomerCell.getCellType() != Cell.CELL_TYPE_STRING) {
						importResponses.add(new ImportResponseDto(false, responseTitle, "Le type de client null !"));
						continue;
					} else {
						typeCustomer = typeCustomerRepository.findByName(typeCustomerCell.getStringCellValue());
						if (typeCustomer == null) {
							importResponses
									.add(new ImportResponseDto(false, responseTitle, "Type de client non trouvé !"));
							continue;
						}
					}

					/**
					 * Code client
					 */
					Cell codeCustomerCell = row.getCell(2);
					if (codeCustomerCell != null && codeCustomerCell.getCellType() == Cell.CELL_TYPE_STRING) {
						customerCode = codeCustomerCell.getStringCellValue();
					}
				}

				/**
				 * Commerciaux
				 */
				commercials.clear();
				Cell commercialCell = row.getCell(4);
				if (commercialCell != null && commercialCell.getCellType() == Cell.CELL_TYPE_STRING) {
					commercial = commercialRepository.findByName(commercialCell.getStringCellValue());
					if (commercial == null) {
						importResponses.add(new ImportResponseDto(false, responseTitle,
								"Commercial «" + commercialCell.getStringCellValue() + "» non trouvé !"));
						continue;
					} else {
						commercials.add(commercial);
					}
				}

				/**
				 * Loalité
				 */
				localityDto = new LocalityDto();

				/**
				 * Adresse
				 */
				Cell addressCell = row.getCell(5);

				if (addressCell != null && addressCell.getCellType() == Cell.CELL_TYPE_STRING) {
					localityDto.setAddress(addressCell.getStringCellValue());
				}

				/**
				 * Latitude
				 */
				Cell latitudeCell = row.getCell(6);

				if (latitudeCell != null && latitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLatitude(latitudeCell.getNumericCellValue());
				}

				/**
				 * Longitude
				 */
				Cell longitudeCell = row.getCell(7);

				if (longitudeCell != null && longitudeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					localityDto.setLongitude(longitudeCell.getNumericCellValue());
				}

				/**
				 * Nom de locality
				 */
				localityDto.setName(tiersAdd.getName());

				/**
				 * Validation de dto locality
				 */
				errors = Utils.validateDto(localityDto);
				if (errors.size() > 0) {
					for (String error : errors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				} else {
					locality = modelMapper.map(localityDto, Locality.class);
					locality.setId(null);
					localityRepository.save(locality);
					tiersAdd.setLocality(localityDto);
				}

				/**
				 * CIN
				 */
				Cell cinCell = row.getCell(8);

				if (cinCell != null && cinCell.getCellType() == Cell.CELL_TYPE_STRING) {
					tiersAdd.setCin(cinCell.getStringCellValue());
				}

				/**
				 * Tél1
				 */
				Cell phone1Cell = row.getCell(9);
				if (phone1Cell != null && phone1Cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					tiersAdd.setPhone1(phoneDecimalFormat.format((long) phone1Cell.getNumericCellValue()));
				}

				/**
				 * Tél2
				 */
				Cell phone2Cell = row.getCell(10);
				if (phone2Cell != null && phone2Cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					tiersAdd.setPhone2(phoneDecimalFormat.format((long) phone2Cell.getNumericCellValue()));
				}

				/**
				 * Fax
				 */
				Cell faxCell = row.getCell(11);
				if (faxCell != null && faxCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					tiersAdd.setFax(phoneDecimalFormat.format((long) faxCell.getNumericCellValue()));
				}

				/**
				 * Email
				 */
				Cell emailCell = row.getCell(12);
				if (emailCell != null && emailCell.getCellType() == Cell.CELL_TYPE_STRING) {
					tiersAdd.setEmail(emailCell.getStringCellValue());
				}

				/**
				 * RIB
				 */
				Cell ribCell = row.getCell(13);
				if (ribCell != null && ribCell.getCellType() == Cell.CELL_TYPE_STRING) {
					tiersAdd.setRib(ribCell.getStringCellValue());
				}

				/**
				 * Patente
				 */
				Cell PatentCell = row.getCell(14);
				if (PatentCell != null && PatentCell.getCellType() == Cell.CELL_TYPE_STRING) {
					tiersAdd.setPatent(PatentCell.getStringCellValue());
				}

				/**
				 * Date de création
				 */
				tiersAdd.setCreateAt(new Date());

				/**
				 * Validation de dto tiers OU client
				 */
				if (isCustomer) {
					customerAdd = modelMapper.map(tiersAdd, CustomerAddDto.class);
					customerAdd.setTypeCustomer(typeTiers.getId());
					customerAdd.setCode(customerCode);

					errors = Utils.validateDto(customerAdd);
				} else {
					errors = Utils.validateDto(tiersAdd);
				}

				if (errors.size() > 0) {
					for (String error : errors) {
						importResponses.add(new ImportResponseDto(false, responseTitle, error));
					}
					continue;
				}

				/**
				 * Enregistrer
				 */
				if (isCustomer) {
					customer = modelMapper.map(customerAdd, Customer.class);
					customer.setId(null);
					customer.setTypeTiers(typeTiers);
					customer.setTypeCustomer(typeCustomer);
					customer.setLocality(locality);
					customer.setCommercials(commercials);

					customerRepository.save(customer);
				} else {
					tiers = modelMapper.map(tiersAdd, Tiers.class);
					tiers.setId(null);
					tiers.setTypeTiers(typeTiers);
					tiers.setLocality(locality);
					tiers.setCommercials(commercials);

					tiersRepository.save(tiers);
				}

				importResponses.add(new ImportResponseDto(true, responseTitle, "Importé"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return importResponses;
	}

}
