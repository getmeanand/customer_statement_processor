package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.utils.RaboUtils;

public class RaboXMLParser extends RaboFileParser {

	@Override
	public List<RaboCustomerAccounts> validateCustomerDataList(List<RaboCustomerAccounts> customerFileList) {
		return RaboUtils.validateCustomerRecords(customerFileList);
	}

	@Override
	public RaboCustomerAccounts parseCustomerInformation(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
