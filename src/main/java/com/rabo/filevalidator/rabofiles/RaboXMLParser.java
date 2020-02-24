package com.rabo.filevalidator.rabofiles;

import java.util.List;

import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboutils.RaboUtils;

/**
 * @author Anandha
 *
 */
public class RaboXMLParser extends RaboFileParser {

	/**
	 * Validating the customer file input records and send back the failure records
	 * to the caller .
	 * 
	 * @param customerFileList
	 * @return List<RaboCustomerAccounts>
	 */
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
