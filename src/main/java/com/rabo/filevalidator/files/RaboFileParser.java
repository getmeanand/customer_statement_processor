package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.dto.RaboCustomerAccounts;

public abstract class RaboFileParser {
	public abstract RaboCustomerAccounts parseCustomerInformation(String data);

	public abstract List<RaboCustomerAccounts> validateCustomerDataList(List<RaboCustomerAccounts> customerFileList);

}
