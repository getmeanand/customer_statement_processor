package com.rabo.filevalidator.validatorfiles;

import java.util.List;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.utils.FileValidatorUtils;

/**
 * @author Anandha
 *
 */
public class XMLParser extends CustomerFileParser {

	/**
	 * Validating the customer file input records and send back the failure records
	 * to the caller .
	 * 
	 * @param customerFileList
	 * @return List<RaboCustomerAccounts>
	 */
	@Override
	public List<CustomerAccounts> validateCustomerDataList(List<CustomerAccounts> customerFileList) {
		return FileValidatorUtils.validateCustomerRecords(customerFileList);
	}

	@Override
	public CustomerAccounts parseCustomerInformation(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
