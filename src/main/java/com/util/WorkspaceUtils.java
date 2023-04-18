package com.util;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class WorkspaceUtils {

	/**
	 * =========================================================================
	 * 
	 * SAVE STATEMENT !
	 * new posix ==> rwxrwxrwx
	 * old posix ==> rwxr-xr--
	 * 
	 * =========================================================================
	 */
	public static boolean saveFile(String targetFolder, MultipartFile file) {
		if (file.isEmpty()) {
			return false;
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(targetFolder + file.getOriginalFilename());
	
			Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr--");

			Files.write(path, bytes);
			Files.setPosixFilePermissions(path, permissions);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static boolean saveFile(String targetFolder, MultipartFile file, String fileName) {
		if (file.isEmpty()) {
			return false;
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(targetFolder + fileName + ".png");
						
			Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxr-xr-x");
			FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);

			Files.createDirectories(Paths.get(targetFolder), fileAttributes );
			
			Set<PosixFilePermission> permissionsFile = PosixFilePermissions.fromString("rwxr-xr--");
			
			Files.write(path, bytes);
			Files.setPosixFilePermissions(path, permissionsFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * =========================================================================
	 * 
	 * CHECK STATEMENT !
	 * 
	 * =========================================================================
	 */

	/**
	 * checkIfFileExist : check if file exist !
	 */
	public static boolean checkIfFileExist(String targetFolder, String folderName) {
		File f = new File(targetFolder + folderName);
		// check if file exist & is not directory
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

}