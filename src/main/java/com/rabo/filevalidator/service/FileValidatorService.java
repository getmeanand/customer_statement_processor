package com.rabo.filevalidator.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.controller.FileValidatorController;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperations;
import com.rabo.filevalidator.operations.FileOperationsFactory;
import com.rabo.filevalidator.utils.FileValidatorUtils;

/**
 * RaboService class contain the functional implementation of Rabobank customer
 * statement processing and validations
 * 
 * @author Anandha
 *
 */
@Service
public class FileValidatorService {

	private static final Logger logger = LoggerFactory.getLogger(FileValidatorController.class);

	@Autowired
	private FileOperationsFactory fileFactory;
	List<CustomerAccounts> filteredCustomerList;

	/**
	 * this function gets the file factory instance of csv and loads the customer
	 * statement files of type .csv
	 * 
	 * @param csvFile
	 * @return
	 * @throws CustomerFileNotFoundException
	 * @throws IOException
	 */
	public List<CustomerAccounts> loadAndProcessCSVFile(MultipartFile file)
			throws CustomerFileNotFoundException, IOException {
		FileOperations csvFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.CSV);

		return csvFileOperation.readCustomerValidatorFile(file);
	}

	/**
	 * this function gets the file factory instance of xml and loads the customer
	 * statement files of type .xml
	 * 
	 * @param xmlFile
	 * @return
	 * @throws CustomerFileNotFoundException
	 * @throws IOException
	 */
	public List<CustomerAccounts> loadAndProcessXMLFile(MultipartFile xmlFile)
			throws CustomerFileNotFoundException, IOException {
		FileOperations xmlFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.XML);

		return xmlFileOperation.readCustomerValidatorFile(xmlFile);

	}

	/**
	 * Function Accept the user input file and start processing on it. finally it
	 * will return the list of failure customer records.
	 * 
	 * @param file
	 * @return
	 * @throws CustomerFileNotFoundException
	 * @throws IOException
	 */
	public List<CustomerAccounts> processCustomerFiles(MultipartFile customerFile)
			throws CustomerFileNotFoundException, IOException {
		String filename = StringUtils.cleanPath(customerFile.getOriginalFilename());

		if (filename.endsWith(FileValidatorConstants.FILE_TYPE_CSV)
				|| filename.endsWith(FileValidatorConstants.FILE_TYPE_XML)) {

			if (FileValidatorUtils.getCustomerFileExtension(customerFile.getOriginalFilename())
					.equalsIgnoreCase(FileValidatorConstants.FILE_TYPE_CSV)) {
				filteredCustomerList = loadAndProcessCSVFile(customerFile);
			} else {
				filteredCustomerList = loadAndProcessXMLFile(customerFile);
			}

		} else {
			logger.error("Customer file Not Found of type .csv .xml Exception");
			throw new CustomerFileNotFoundException();
		}

		return filteredCustomerList;

	}
}
