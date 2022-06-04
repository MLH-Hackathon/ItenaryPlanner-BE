package com.example.itenaryplanner.util;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.exception.UploadFileException;

public class FileUtils {

	private final static Set<String> FILE_EXT_ALLOWED = Set.of(".png", ".jpg", "jpeg");
	private final static int MAX_FILE_SIZE_ALLOWED = 1 * 1024;

	public static boolean validateFile(MultipartFile file) {
		if (file == null) {
			return true;
		}

		final String fileName = file.getOriginalFilename();
		final int dotIndex = fileName.lastIndexOf('.');
		String ext = dotIndex != -1 ? fileName.substring(dotIndex) : fileName;

		if (!FILE_EXT_ALLOWED.contains(ext.toLowerCase())) {
			throw new UploadFileException("Only jpg, jpeg & png allowed");
		}

		if (MAX_FILE_SIZE_ALLOWED < (file.getSize() / MAX_FILE_SIZE_ALLOWED)) {
			throw new UploadFileException(String.format("Upto %s Kb file size allowed.", MAX_FILE_SIZE_ALLOWED));
		}
		
		return true;
	}
}
