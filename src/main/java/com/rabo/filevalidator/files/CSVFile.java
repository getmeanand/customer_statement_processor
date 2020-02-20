package com.rabo.filevalidator.files;

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

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.controller.RaboController;
import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.operations.FileOperations;

@Component
public class CSVFile extends FileOperations {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RaboController.class);

	private List<Record> customerDataList = null;

	@Override
	public List<Record> readCustomerValidatorFile(File csvFile) {
		CSVParser csvParser = new CSVParser();
		File inputF = new File(csvFile.toString());

		try (InputStream inputFS = new FileInputStream(inputF);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputFS))) {

			List<Record> csvFileInformationList = br.lines().skip(1).map(csvParser::parseCustomerInformation)
					.collect(Collectors.toList());

			if (csvFileInformationList != null && csvFileInformationList.size() != RaboConstants.INT_VAL_ZERO) {
				customerDataList = csvParser.validateCustomerDataList(csvFileInformationList);
			}

		} catch (IOException e) {
		}

		return customerDataList;
	}

}
