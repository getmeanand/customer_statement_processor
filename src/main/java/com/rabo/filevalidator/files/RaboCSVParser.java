package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.utils.RaboUtils;

/**
 * @author Anandha
 *
 */
public class RaboCSVParser extends RaboFileParser {

	/**
	 * this function parse the customer data and parse it
	 * 
	 * @param customerData
	 * @return RaboCustomerAccounts
	 */
	@Override
	public RaboCustomerAccounts parseCustomerInformation(final String customerData) {

		RaboCustomerAccounts record = null;

		String[] strCustomerDetails = customerData.split(RaboConstants.COMMA_DELIMITER);
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
	public List<RaboCustomerAccounts> validateCustomerDataList(List<RaboCustomerAccounts> customerDataList) {
		return RaboUtils.validateCustomerRecords(customerDataList);

	}

	/** Spliting the customer datas based on the "," delimitor
	 * @param splitRecDetails
	 * @return
	 */
	public RaboCustomerAccounts parseSplitedValues(String[] splitRecDetails) {
		RaboCustomerAccounts record = null;
		if (!nullCheck(splitRecDetails)) {
			int index = RaboConstants.INT_VAL_ZERO;
			if (splitRecDetails != null) {
				record = new RaboCustomerAccounts();
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
		if (value.length == RaboConstants.INT_VAL_ZERO) {
			return true;
		}
		for (String strElement : value) {
			if (strElement == null) {
				return true;
			}
			if (strElement.length() == RaboConstants.INT_VAL_ZERO) {
				return true;
			}
			if (strElement.isEmpty()) {
				return true;
			}
		}
		return false;

	}

}
