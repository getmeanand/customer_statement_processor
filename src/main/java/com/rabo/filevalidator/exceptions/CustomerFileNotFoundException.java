package com.rabo.filevalidator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Anandha
 *
 */
@ResponseStatus(code=HttpStatus.NOT_FOUND,reason="Error in Reading the Customer File!!")
 public class CustomerFileNotFoundException extends RuntimeException {

	

}
