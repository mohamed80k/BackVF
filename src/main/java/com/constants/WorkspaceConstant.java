package com.constants;

public class WorkspaceConstant {

	/**
	 * =========================================================================
	 * 
	 * FOLDER PROPERTIES
	 * 
	 * =========================================================================
	 */
	// public static final String FILE_DIR =
	public static final String FILE_DIR = "/var/www/html/files/menara/commercial/";

	public static final String FILE_DIR_RELATIVE = "/files/menara/commercial/";
	
	public static final String CUSTOMER_FOLDER = "customer//";


	/**
	 * =========================================================================
	 * 
	 * FOLDER PATTERN PROPERTIES
	 * 
	 * =========================================================================
	 */

	public static final String CUSTOMER_ACCOUNT_FOLDER_PATTERN = FILE_DIR + "{ID_COMMERCIAL}//" + CUSTOMER_FOLDER + "{REF_CUSTOMER}//";

	public static final String CUSTOMER_ACCOUNT_FOLDER_PATTERN_RELATIVE = FILE_DIR_RELATIVE + "{ID_COMMERCIAL}//" + CUSTOMER_FOLDER + "{REF_CUSTOMER}//";



	/**
	 * =========================================================================
	 * 
	 * FILE PROPERTIES
	 * 
	 * =========================================================================
	 */
	public static final String CUSTOMER_SIGNATURE = "customer_signature";

	public static final String COMMERCIAL_SIGNATURE = "commercial_signature";
	
	/**
	 * =========================================================================
	 * 
	 * FILE PATTERN PROPERTIES
	 * 
	 * =========================================================================
	 */


	public static final String CUSTOMER_ACCOUNT_PATTERN = CUSTOMER_ACCOUNT_FOLDER_PATTERN_RELATIVE + CUSTOMER_SIGNATURE+ ".{EXT}";
	public static final String COMMERCIAL_CUSTOMER_ACCOUNT_PATTERN = CUSTOMER_ACCOUNT_FOLDER_PATTERN_RELATIVE + COMMERCIAL_SIGNATURE+ ".{EXT}";


	public static String generateFolderCustomerSignatureUri(int commercialId, String ref) {
		return CUSTOMER_ACCOUNT_FOLDER_PATTERN.replace("{ID_COMMERCIAL}", String.valueOf(commercialId))
				.replace("{REF_CUSTOMER}", ref).replace("{EXT}", "png").replace("//", "/");
	}

	public static String generateCustomerSignatureUri(Integer commercialId, String ref) {
		return CUSTOMER_ACCOUNT_PATTERN.replace("{ID_COMMERCIAL}", String.valueOf(commercialId))
				.replace("{REF_CUSTOMER}", ref).replace("{EXT}", "png").replace("//", "/");
	}
	
	public static String generateFolderCommercialSignatureUri(int commercialId, String ref) {
		return CUSTOMER_ACCOUNT_FOLDER_PATTERN.replace("{ID_COMMERCIAL}", String.valueOf(commercialId))
				.replace("{REF_CUSTOMER}",ref).replace("{EXT}", "png").replace("//", "/");
	}

	public static String generateCommercialSignatureUri(Integer commercialId, String ref) {
		return COMMERCIAL_CUSTOMER_ACCOUNT_PATTERN.replace("{ID_COMMERCIAL}", String.valueOf(commercialId))
				.replace("{REF_CUSTOMER}", ref).replace("{EXT}", "png").replace("//", "/");
	}

	
}