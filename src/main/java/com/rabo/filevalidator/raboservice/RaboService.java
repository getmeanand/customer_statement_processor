package com.rabo.filevalidator.raboservice;

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

import com.rabo.filevalidator.raboconstants.RaboConstants;
import com.rabo.filevalidator.rabocontroller.RaboController;
import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboenums.FILE_TYPE;
import com.rabo.filevalidator.raboexceptions.RaboCustomerFileSaveException;
import com.rabo.filevalidator.raboexceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.rabooperations.RaboFileOperations;
import com.rabo.filevalidator.rabooperations.RaboFileOperationsFactory;
import com.rabo.filevalidator.raboutils.RaboUtils;

/**
 * RaboService class contain the functional implementation of Rabobank customer
 * statement processing and validations
 * 
 * @author Anandha
 *
 */
@Service
public class RaboService {

	private static final Logger logger = LoggerFactory.getLogger(RaboController.class);

	@Autowired
	private RaboFileOperationsFactory fileFactory;

	/**
	 * processAndValidateCustomerFiles() loads the Customer statement files and
	 * start validating based on the validation criteria and finally return list of
	 * failure customer records
	 * 
	 * @return
	 * @throws RaboFileNotFoundException
	 * @throws IOException
	 */
	public List<RaboCustomerAccounts> processAndValidateCustomerFiles() throws RaboFileNotFoundException, IOException {

		List<RaboCustomerAccounts> filteredCustomerList = new ArrayList<RaboCustomerAccounts>();

		List<File> filesInFolder = loadAllFilesFromPath();
		filesInFolder.forEach(customerFile -> {
			String fileType = RaboUtils.getCustomerFileExtension(customerFile);

			if (fileType.equalsIgnoreCase(RaboConstants.FILE_TYPE_CSV)) {
				filteredCustomerList.addAll(loadAndProcessCSVFile(customerFile));
				RaboUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						RaboUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());

			} else {
				filteredCustomerList.addAll(loadAndProcessXMLFile(customerFile));
				RaboUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						RaboUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());
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
	 * @throws RaboFileNotFoundException
	 */
	public List<RaboCustomerAccounts> loadAndProcessCSVFile(File csvFile) throws RaboFileNotFoundException {
		RaboFileOperations csvFileOperation = (RaboFileOperations) fileFactory.getFileInstance(FILE_TYPE.CSV);

		return csvFileOperation.readCustomerValidatorFile(csvFile);
	}

	/**
	 * this function gets the file factory instance of xml and loads the customer
	 * statement files of type .xml
	 * 
	 * @param xmlFile
	 * @return
	 * @throws RaboFileNotFoundException
	 */
	public List<RaboCustomerAccounts> loadAndProcessXMLFile(File xmlFile) throws RaboFileNotFoundException {
		RaboFileOperations xmlFileOperation = (RaboFileOperations) fileFactory.getFileInstance(FILE_TYPE.XML);

		return xmlFileOperation.readCustomerValidatorFile(xmlFile);

	}

	/**
	 * It loads all the files from the customer files location and return list of
	 * files
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<File> loadAllFilesFromPath() throws IOException {
		List<File> filesInPhysicalPathList;

		filesInPhysicalPathList = Files.walk(RaboUtils.fileStorageLocation).filter(Files::isRegularFile)
				.map(Path::toFile).collect(Collectors.toList());
		if (filesInPhysicalPathList.isEmpty()) {
			throw new RaboFileNotFoundException();
		}

		return filesInPhysicalPathList;
	}

	/**
	 * This Method is used to store the user input file in the physical path for
	 * validating it.
	 * 
	 * @param file
	 * @return
	 * @throws RaboCustomerFileSaveException
	 */
	public String storeCustomerFiles(MultipartFile file) throws RaboCustomerFileSaveException {

		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if (filename.endsWith(RaboConstants.FILE_TYPE_CSV) || filename.endsWith(RaboConstants.FILE_TYPE_XML)) {

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, RaboUtils.fileStorageLocation.resolve(filename),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e) {
				logger.error("Error on Customer Files Store:: " + e.getMessage());
				throw new RaboCustomerFileSaveException();
			}
		}
		return filename;
	}

}
