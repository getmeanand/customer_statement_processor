package com.rabo.filevalidator.rabooperations;

import java.io.File;
import java.util.List;

import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboexceptions.RaboFileNotFoundException;

/**
 * @author Anandha
 *
 */
public abstract class RaboFileOperations {
	public abstract List<RaboCustomerAccounts> readCustomerValidatorFile(File file) throws RaboFileNotFoundException;

	

}
