package com.rabo.filevalidator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="Error in Saving Customer File!!")
 public class RaboCustomerFileSaveException extends RuntimeException {

	

}
