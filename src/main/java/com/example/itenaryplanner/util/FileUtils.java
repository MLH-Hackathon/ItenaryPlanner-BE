package com.example.itenaryplanner.util;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.example.itenaryplanner.exception.UploadFileException;

public class FileUtils {

	private final static Set<String> FILE_EXT_ALLOWED = Set.of(".png", ".jpg", "jpeg");
	private final static int MAX_FILE_SIZE_ALLOWED = 1 * 1024;
	private final static int MAX_TRIP_FILE_SIZE_ALLOWED = 10 * 1024;
	private final static int MAX_PROFILE_SIZE_ALLOWED = 3 * 1024;

	private static boolean isFileExtValid(final String fileName) {
		final int dotIndex = fileName.lastIndexOf('.');
		String ext = dotIndex != -1 ? fileName.substring(dotIndex) : fileName;

		return FILE_EXT_ALLOWED.contains(ext.toLowerCase());
	}

	public static boolean validateFile(MultipartFile file) {
		if (file == null) {
			return true;
		}

		if (!isFileExtValid(file.getOriginalFilename())) {
			throw new UploadFileException("Only jpg, jpeg & png allowed");
		}

		if (MAX_FILE_SIZE_ALLOWED < (file.getSize() / MAX_FILE_SIZE_ALLOWED)) {
			throw new UploadFileException(String.format("Upto %s Kb file size allowed.", MAX_FILE_SIZE_ALLOWED));
		}
		
		return true;
	}

	public static boolean validateTripFile(MultipartFile file) {
		if (file == null) {
			return true;
		}

		if (!isFileExtValid(file.getOriginalFilename())) {
			throw new UploadFileException("Only jpg, jpeg & png allowed");
		}

		if (MAX_TRIP_FILE_SIZE_ALLOWED < (file.getSize() / MAX_TRIP_FILE_SIZE_ALLOWED)) {
			throw new UploadFileException(String.format("Upto %s Kb file size allowed.", MAX_TRIP_FILE_SIZE_ALLOWED));
		}
		
		return true;
	}

	public static boolean validateProfilePic(MultipartFile file) {
		if (file == null) {
			throw new UploadFileException("Profile pic not found");
		}

		if (!isFileExtValid(file.getOriginalFilename())) {
			throw new UploadFileException("Only jpg, jpeg & png allowed");
		}

		if (MAX_PROFILE_SIZE_ALLOWED < (file.getSize() / MAX_PROFILE_SIZE_ALLOWED)) {
			throw new UploadFileException(String.format("Upto %s Kb file size allowed.", MAX_PROFILE_SIZE_ALLOWED));
		}
		
		return true;
	}
}
