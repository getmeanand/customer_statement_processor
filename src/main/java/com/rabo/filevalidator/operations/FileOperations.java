package com.rabo.filevalidator.operations;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;

/**
 * @author Anandha
 *
 */
public abstract class FileOperations {
	public abstract List<CustomerAccounts> readCustomerValidatorFile(MultipartFile csvFile) throws CustomerFileNotFoundException, IOException;

	

}
