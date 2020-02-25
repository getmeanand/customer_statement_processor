package com.rabo.filevalidator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.service.FileValidatorService;
import com.rabo.filevalidator.utils.FileValidatorUtils;

/**
 * @author Anandha
 *
 */
@RestController
public class FileValidatorController {

	private static final Logger logger = LoggerFactory.getLogger(FileValidatorUtils.class);

	@Autowired
	FileValidatorService raboService;

	/**
	 * This uploadMultipleFiles() deals with loading the customer statement files
	 * and start processing the validation. finally it will return the list of
	 * failure records
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws CustomerFileNotFoundException, IOException
	 */
	@PostMapping("/uploadCustomerFiles")
	@ResponseBody
	public ResponseEntity<List<CustomerAccounts>> uploadMultipleFiles(@RequestParam("customerFile") MultipartFile file)
			throws IOException, CustomerFileNotFoundException {
		try {

			return Optional.ofNullable(raboService.processCustomerFiles(file))
					.map(customerInfo -> new ResponseEntity<>(customerInfo, HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.CONFLICT));

		} catch (Exception e) {
			logger.error("Exception in Processing Customer File::" + file.getOriginalFilename()+" "+e.getMessage()  );
			throw new CustomerFileNotFoundException();
		

		}

	}

}
