package com.rabo.filevalidator.rabofiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.filevalidator.raboconstants.RaboConstants;
import com.rabo.filevalidator.rabocontroller.RaboController;
import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboexceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.rabooperations.RaboFileOperations;

/**
 * @author Anandha
 *
 */
@Component
public class RaboCSVFile extends RaboFileOperations {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RaboController.class);

	private List<RaboCustomerAccounts> customerDataList = null;

	/**
	 * This readCustomerValidatorFile() reads the customer statement files from the
	 * path and start validating it. finally it will return the list of failure
	 * records
	 * 
	 * @param csvFile
	 * @return
	 */
	@Override
	public List<RaboCustomerAccounts> readCustomerValidatorFile(File csvFile) {
		RaboCSVParser csvParser = new RaboCSVParser();
		File inputF = new File(csvFile.toString());

		try (InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {

			List<RaboCustomerAccounts> csvFileInformationList = br.lines().skip(1)
					.map(csvParser::parseCustomerInformation).collect(Collectors.toList());

			if (csvFileInformationList != null && csvFileInformationList.size() != RaboConstants.INT_VAL_ZERO) {
				customerDataList = csvParser.validateCustomerDataList(csvFileInformationList);
			}

		} catch (IOException e) {
			logger.error("Error in Read Customer file::" + e.getMessage());
		}

		return customerDataList;
	}

}
