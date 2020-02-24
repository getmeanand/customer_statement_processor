package com.rabo.filevalidator.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.rabo.filevalidator.exceptions.CustomerFileSaveException;
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
	List<CustomerAccounts> filteredCustomerList ;

	/**
	 * processAndValidateCustomerFiles() loads the Customer statement files and
	 * start validating based on the validation criteria and finally return list of
	 * failure customer records.
	 * 
	 * @return
	 * @throws CustomerFileNotFoundException
	 * @throws IOException
	 */
	public List<CustomerAccounts> processAndValidateCustomerFiles() throws CustomerFileNotFoundException, IOException {

		filteredCustomerList = new ArrayList<CustomerAccounts>();

		List<File> filesInFolder = loadAllFilesFromPath();
		filesInFolder.forEach(customerFile -> {
			String fileType = FileValidatorUtils.getCustomerFileExtension(customerFile);

			if (fileType.equalsIgnoreCase(FileValidatorConstants.FILE_TYPE_CSV)) {
				filteredCustomerList.addAll(loadAndProcessCSVFile(customerFile));
				FileValidatorUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						FileValidatorUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());

			} else {
				filteredCustomerList.addAll(loadAndProcessXMLFile(customerFile));
				FileValidatorUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						FileValidatorUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());
			}

		});

		return filteredCustomerList;
	}

	/**
	 * this function gets the file factory instance of csv and loads the customer
	 * statement files of type .csv
	 * 
	 * @param csvFile
	 * @return
	 * @throws CustomerFileNotFoundException
	 */
	public List<CustomerAccounts> loadAndProcessCSVFile(File csvFile) throws CustomerFileNotFoundException {
		FileOperations csvFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.CSV);

		return csvFileOperation.readCustomerValidatorFile(csvFile);
	}

	/**
	 * this function gets the file factory instance of xml and loads the customer
	 * statement files of type .xml
	 * 
	 * @param xmlFile
	 * @return
	 * @throws CustomerFileNotFoundException
	 */
	public List<CustomerAccounts> loadAndProcessXMLFile(File xmlFile) throws CustomerFileNotFoundException {
		FileOperations xmlFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.XML);

		return xmlFileOperation.readCustomerValidatorFile(xmlFile);

	}

	/**
	 * It loads, all the files from the customer files location and return list of
	 * files
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<File> loadAllFilesFromPath() throws IOException {
		List<File> filesInPhysicalPathList;

		filesInPhysicalPathList = Files.walk(FileValidatorUtils.fileStorageLocation).filter(Files::isRegularFile)
				.map(Path::toFile).collect(Collectors.toList());
		if (filesInPhysicalPathList.isEmpty()) {
			throw new CustomerFileNotFoundException();
		}

		return filesInPhysicalPathList;
	}

	/**
	 * This Method is used to store the user input file in the physical path for
	 * validating it.
	 * 
	 * @param file
	 * @return
	 * @throws CustomerFileSaveException
	 */
	public String storeCustomerFiles(MultipartFile file) throws CustomerFileSaveException {

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if (filename.endsWith(FileValidatorConstants.FILE_TYPE_CSV) || filename.endsWith(FileValidatorConstants.FILE_TYPE_XML)) {

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, FileValidatorUtils.fileStorageLocation.resolve(filename),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				logger.error("Error on Customer Files Store:: " + e.getMessage());
				throw new CustomerFileSaveException();
			}
		}else {
			throw new CustomerFileNotFoundException();
		}
		return filename;
	}

}
