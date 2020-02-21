package com.rabo.filevalidator.operations;

import java.io.File;
import java.util.List;

import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.exceptions.RaboFileNotFoundException;

public abstract class RaboFileOperations {
	public abstract List<RaboCustomerAccounts> readCustomerValidatorFile(File file) throws RaboFileNotFoundException;

	

}
