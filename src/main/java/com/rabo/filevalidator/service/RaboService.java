package com.rabo.filevalidator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.controller.RaboController;
import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperations;
import com.rabo.filevalidator.operations.FileOperationsFactory;
import com.rabo.filevalidator.utils.RaboUtils;

@Service
public class RaboService {

	@Autowired
	RaboUtils raboUtils;

	@Autowired
	private FileOperationsFactory fileFactory;

	public List<Record> processAndValidateCustomerFiles() throws CustomerFileNotFoundException {

		List<Record> filteredCustomerList = new ArrayList<Record>();

		List<File> filesInFolder = loadAllFilesFromPath();
		filesInFolder.forEach(customerFile -> {
			String fileType = RaboUtils.getFileExtension(customerFile);

			if (fileType.equalsIgnoreCase(RaboConstants.CSV)) {
				filteredCustomerList.addAll(loadAndProcessCSVFile(customerFile));
				RaboUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						raboUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());

			} else {
				filteredCustomerList.addAll(loadAndProcessXMLFile(customerFile));
				RaboUtils.moveFilesToProcesedFolder(customerFile.getAbsolutePath(),
						raboUtils.fileProcessedStorageLocation + "\\" + customerFile.getName());
			}

		});

		return filteredCustomerList;
	}

	private static final Logger logger = LoggerFactory.getLogger(RaboController.class);

	public List<Record> loadAndProcessCSVFile(File csvFile) throws CustomerFileNotFoundException {
		FileOperations csvFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.CSV);

		return csvFileOperation.readCustomerValidatorFile(csvFile);
	}

	public List<Record> loadAndProcessXMLFile(File xmlFile) throws CustomerFileNotFoundException {
		FileOperations xmlFileOperation = (FileOperations) fileFactory.getFileInstance(FILE_TYPE.XML);

		return xmlFileOperation.readCustomerValidatorFile(xmlFile);

	}

	public List<File> loadAllFilesFromPath() {
		List<File> filesInPhysicalPathList;
		try {
			filesInPhysicalPathList = Files.walk(raboUtils.fileStorageLocation).filter(Files::isRegularFile)
					.map(Path::toFile).collect(Collectors.toList());
		} catch (IOException e) {
			logger.error("Error Reading file from the storagelocation::" + e.getMessage());
			throw new CustomerFileNotFoundException();
		}
		return filesInPhysicalPathList;
	}

}
