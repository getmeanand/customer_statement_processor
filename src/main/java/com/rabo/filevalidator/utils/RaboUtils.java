package com.rabo.filevalidator.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.service.FileStorageProperties;
import com.rabo.filevalidator.service.RaboService;

@Component
public class RaboUtils {
	private static final Logger logger = LoggerFactory.getLogger(RaboUtils.class);

	@Autowired
	RaboService raboService;

	public Path fileStorageLocation;
	public Path fileProcessedStorageLocation;

	@Autowired
	public RaboUtils(FileStorageProperties fileStorageProperties, FileStorageProperties fileProcedssedStorageLocation)
			throws IOException {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getLocation()).toAbsolutePath().normalize();
		this.fileProcessedStorageLocation = Paths.get(fileStorageProperties.getProcessed()).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.fileStorageLocation);
		Files.createDirectories(this.fileProcessedStorageLocation);
	}

	public static List<Record> validateCustomerRecords(List<Record> paramList) {
		Set<String> setReferenceData = new HashSet<>();

		return paramList.stream().map(record -> validateCustomerDataColumns(record, setReferenceData))
				.filter(Objects::nonNull).collect(Collectors.toList());

	}

	private static Record validateCustomerDataColumns(Record paramValue, Set<String> setDataCheck) {
		Record failedRecord = null;

		if (!setDataCheck.add(paramValue.getReference())) {
			failedRecord = paramValue;
		} else {
			double endBalance = Double.valueOf(
					RaboConstants.DECIMAL_FORMAT.format(paramValue.getStartBalance() + paramValue.getMutation()));
			if (endBalance != paramValue.getEndBalance())
				failedRecord = paramValue;

		}
		return failedRecord;
	}

	public static void moveFilesToProcesedFolder(String src, String dest) {

		Path result = null;
		try {
			result = Files.move(Paths.get(src), Paths.get(dest));
		} catch (IOException e) {
			logger.error("Exception while moving file: " + e.getMessage());
		}
		if (result != null) {
			logger.info("File moved successfully." + dest);
		} else {
			logger.info("File movement failed to " + dest);
		}

	}

	public static String getFileExtension(File customerFile) {
		if (customerFile == null) {
			return "";
		}
		String fileName = customerFile.getName();
		int i = fileName.lastIndexOf('.');
		String ext = i > 0 ? fileName.substring(i + 1) : "";
		return ext;
	}

}
