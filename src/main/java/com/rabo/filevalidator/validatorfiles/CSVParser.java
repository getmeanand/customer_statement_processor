package com.rabo.filevalidator.validatorfiles;

import java.util.List;

import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.utils.FileValidatorUtils;

/**
 * @author Anandha
 *
 */
public class CSVParser extends CustomerFileParser {

	/**
	 * this function parse the customer data and parse it
	 * 
	 * @param customerData
	 * @return RaboCustomerAccounts
	 */
	@Override
	public CustomerAccounts parseCustomerInformation(final String customerData) {

		CustomerAccounts record = null;

		String[] strCustomerDetails = customerData.split(FileValidatorConstants.COMMA_DELIMITER);
		if (strCustomerDetails != null) {
			record = parseSplitedValues(strCustomerDetails);
		}

		return record;

	}

	/**
	 * validateCustomerDataList accepts the list of customer data list and start
	 * validating. finally return the failure customer list
	 * 
	 * @param customerDataList
	 * @return List<RaboCustomerAccounts>
	 */
	@Override
	public List<CustomerAccounts> validateCustomerDataList(List<CustomerAccounts> customerDataList) {
		return FileValidatorUtils.validateCustomerRecords(customerDataList);

	}

	/** Spliting the customer datas based on the "," delimitor
	 * @param splitRecDetails
	 * @return
	 */
	public CustomerAccounts parseSplitedValues(String[] splitRecDetails) {
		CustomerAccounts record = null;
		if (!nullCheck(splitRecDetails)) {
			int index = FileValidatorConstants.INT_VAL_ZERO;
			if (splitRecDetails != null) {
				record = new CustomerAccounts();
				record.setReference(splitRecDetails[index++]);
				record.setAccountNumber(splitRecDetails[index++]);
				record.setDescription(splitRecDetails[index++]);
				record.setStartBalance(Double.parseDouble(splitRecDetails[index++]));
				record.setMutation(Double.parseDouble(splitRecDetails[index++]));
				record.setEndBalance(Double.parseDouble(splitRecDetails[index++]));
			}
		}
		return record;
	}

	/**
	 * this function accepts the string of array and check weather the elements are
	 * null or not
	 * 
	 * @param array
	 * @return true or false
	 */
	public boolean nullCheck(String[] value) {
		if (value == null) {
			return true;
		}
		if (value.length == FileValidatorConstants.INT_VAL_ZERO) {
			return true;
		}
		for (String strElement : value) {
			if (strElement == null) {
				return true;
			}
			if (strElement.length() == FileValidatorConstants.INT_VAL_ZERO) {
				return true;
			}
			if (strElement.isEmpty()) {
				return true;
			}
		}
		return false;

	}

}
