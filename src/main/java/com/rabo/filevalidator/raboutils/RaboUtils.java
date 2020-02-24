package com.rabo.filevalidator.raboutils;

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

import com.rabo.filevalidator.raboconstants.RaboConstants;
import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboservice.RaboFileProperties;

/**
 * This is Utilities file for the Rabo Statement Process. it contains
 * implementing and manipulating the Customer input files
 * 
 * @author Anandha
 *
 */
@Component
public class RaboUtils {
	private static final Logger logger = LoggerFactory.getLogger(RaboUtils.class);

	public static Path fileStorageLocation;
	public static Path fileProcessedStorageLocation;

	@Autowired
	public RaboUtils(RaboFileProperties fileStorageProperties, RaboFileProperties fileProcedssedStorageLocation)
			throws IOException {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getLocation()).toAbsolutePath().normalize();
		this.fileProcessedStorageLocation = Paths.get(fileStorageProperties.getProcessed()).toAbsolutePath()
				.normalize();
		Files.createDirectories(this.fileStorageLocation);
		Files.createDirectories(this.fileProcessedStorageLocation);
	}

	/**
	 * Validating the customer account info and return the failure customer list
	 * 
	 * @param paramList
	 * @return
	 */
	public static List<RaboCustomerAccounts> validateCustomerRecords(List<RaboCustomerAccounts> customerAccountList) {
		Set<String> setReferenceData = new HashSet<>();

		return customerAccountList.stream()
				.map(customerData -> validateCustomerDataColumns(customerData, setReferenceData))
				.filter(Objects::nonNull).collect(Collectors.toList());

	}

	/**
	 * Validating the Customer Data and return the failure customer information
	 * 
	 * @param customerData
	 * @param setDataCheck
	 * @return
	 */
	private static RaboCustomerAccounts validateCustomerDataColumns(RaboCustomerAccounts customerData,
			Set<String> setDataCheck) {
		RaboCustomerAccounts failedRecord = null;

		if (!setDataCheck.add(customerData.getReference())) {
			failedRecord = customerData;
		} else {
			double endBalance = Double.valueOf(
					RaboConstants.DECIMAL_FORMAT.format(customerData.getStartBalance() + customerData.getMutation()));
			if (endBalance != customerData.getEndBalance())
				failedRecord = customerData;

		}
		return failedRecord;
	}

	/**
	 * Moving the processed customer files to processed location for the future
	 * reference
	 * 
	 * @param src
	 * @param dest
	 */
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

	/**
	 * Fetching the customer file type like csv or xml
	 * 
	 * @param customerFile
	 * @return
	 */
	public static String getCustomerFileExtension(File customerFile) {
		if (customerFile == null) {
			return "";
		}
		String fileName = customerFile.getName();
		int i = fileName.lastIndexOf('.');
		String ext = i > 0 ? fileName.substring(i + 1) : "";
		return ext;
	}

}
