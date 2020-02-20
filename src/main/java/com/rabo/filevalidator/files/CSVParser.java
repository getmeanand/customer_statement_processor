package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.utils.RaboUtils;

public class CSVParser extends FileParser {

	@Override
	public Record parseCustomerInformation(String data) {

		Record record = null;

		String[] strCustomerDetails = data.split(RaboConstants.COMMA_DELIMITER);
		if (strCustomerDetails != null) {
			record = parseSplitedValues(strCustomerDetails);
		}

		return record;

	}

	@Override
	public List<Record> validateCustomerDataList(List<Record> customerFileList) {
		return RaboUtils.validateCustomerRecords(customerFileList);

	}

	public Record parseSplitedValues(String[] splitRecDetails) {
		Record record = null;
		if (!nullCheck(splitRecDetails)) {
			int index = RaboConstants.INT_VAL_ZERO;
			if (splitRecDetails != null) {
				record = new Record();
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
