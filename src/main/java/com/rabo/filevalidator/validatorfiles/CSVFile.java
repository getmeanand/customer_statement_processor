package com.rabo.filevalidator.validatorfiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.controller.FileValidatorController;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.operations.FileOperations;

/**
 * @author Anandha
 *
 */
@Component
public class CSVFile extends FileOperations {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileValidatorController.class);

	private CSVParser csvParser;

	/**
	 * This readCustomerValidatorFile() reads the customer statement files from the
	 * path and start validating it. finally it will return the list of failure
	 * records
	 * 
	 * @param customerCSVFile
	 * @return
	 * @throws IOException
	 */
	@Override
	public List<CustomerAccounts> readCustomerValidatorFile(MultipartFile customerCSVFile) throws IOException {
		List<CustomerAccounts> customerDataList = null;
		csvParser = new CSVParser();
		try (InputStream inputFS = customerCSVFile.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {

			List<CustomerAccounts> csvFileInformationList = br.lines().skip(1).map(csvParser::parseCustomerInformation)
					.collect(Collectors.toList());

			if (csvFileInformationList != null
					&& csvFileInformationList.size() != FileValidatorConstants.INT_VAL_ZERO) {
				customerDataList = csvParser.validateCustomerDataList(csvFileInformationList);
			}

		} catch (IOException e) {
			logger.error("Error in Read CSV Customer file::" + e.getMessage());
			throw new IOException();
		}

		return customerDataList;
	}

}
