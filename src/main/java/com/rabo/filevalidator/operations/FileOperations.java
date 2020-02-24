package com.rabo.filevalidator.operations;

import java.io.File;
import java.util.List;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;

/**
 * @author Anandha
 *
 */
public abstract class FileOperations {
	public abstract List<CustomerAccounts> readCustomerValidatorFile(File file) throws CustomerFileNotFoundException;

	

}
