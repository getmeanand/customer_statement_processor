package com.rabo.filevalidator.raboexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Anandha
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="Error in Saving Customer File!!")
 public class RaboCustomerFileSaveException extends RuntimeException {

	

}