package com.rabo.filevalidator.rabofiles;

import java.util.List;

import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;

/**
 * @author 454424
 *
 */
public abstract class RaboFileParser {
	public abstract RaboCustomerAccounts parseCustomerInformation(String data);

	public abstract List<RaboCustomerAccounts> validateCustomerDataList(List<RaboCustomerAccounts> customerFileList);

}
