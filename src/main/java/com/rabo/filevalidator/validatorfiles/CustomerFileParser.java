package com.rabo.filevalidator.validatorfiles;

import java.util.List;

import com.rabo.filevalidator.dto.CustomerAccounts;

/**
 * @author 454424
 *
 */
public abstract class CustomerFileParser {
	public abstract CustomerAccounts parseCustomerInformation(String data);

	public abstract List<CustomerAccounts> validateCustomerDataList(List<CustomerAccounts> customerFileList);

}
