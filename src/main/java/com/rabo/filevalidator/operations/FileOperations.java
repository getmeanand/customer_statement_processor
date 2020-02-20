package com.rabo.filevalidator.operations;

import java.io.File;
import java.util.List;

import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;

public abstract class FileOperations {
	public abstract List<Record> readCustomerValidatorFile(File file) throws CustomerFileNotFoundException;

	

}
