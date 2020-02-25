package com.rabo.filevalidator.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.dto.CustomerAccounts;

/**
 * This is Utilities file for the Rabo Statement Process. it contains
 * implementing and manipulating the Customer input files
 * 
 * @author Anandha
 *
 */
@Component
public class FileValidatorUtils {

	/**
	 * Validating the customer account information and return the failure customer
	 * list based on the validation
	 * 
	 * @param customerAccountList
	 * @return
	 */
	public static List<CustomerAccounts> validateCustomerRecords(List<CustomerAccounts> customerAccountList) {
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
	private static CustomerAccounts validateCustomerDataColumns(CustomerAccounts customerData,
			Set<String> setDataCheck) {
		CustomerAccounts failedRecord = null;

		if (!setDataCheck.add(customerData.getReference())) {
			failedRecord = customerData;
		} else {
			double endBalance = Double.valueOf(FileValidatorConstants.DECIMAL_FORMAT
					.format(customerData.getStartBalance() + customerData.getMutation()));
			if (endBalance != customerData.getEndBalance())
				failedRecord = customerData;

		}
		return failedRecord;
	}

	/**
	 * Fetching the customer file type like csv or xml
	 * 
	 * @param customerFile
	 * @return
	 */

	public static String getCustomerFileExtension(String customerFile) {
		if (customerFile == null) {
			return "";
		}
		int i = customerFile.lastIndexOf('.');
		return i > 0 ? customerFile.substring(i + 1) : "";
	}
}
