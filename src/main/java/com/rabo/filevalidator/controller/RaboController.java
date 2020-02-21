package com.rabo.filevalidator.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.exceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.service.RaboService;
import com.rabo.filevalidator.utils.RaboUtils;

/**
 * @author 454424
 *
 */
@RestController
public class RaboController {

	@Autowired
	RaboService raboService;

	/**
	 * This uploadMultipleFiles() deals with loading the customer statement files
	 * and start processing the validation finally it will return the list of
	 * failure records
	 * 
	 * @param files
	 * @return
	 * @throws IOException
	 * @throws RaboFileNotFoundException
	 */
	@PostMapping("/uploadCustomerFiles")
	@ResponseBody
	public ResponseEntity<List<RaboCustomerAccounts>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files)
			throws IOException, RaboFileNotFoundException {
		try {
			List<String> listCustomerFiles = Arrays.stream(files).map(file -> raboService.storeCustomerFiles(file))
					.collect(Collectors.toList());
			if (Optional.ofNullable(listCustomerFiles).isPresent()) {
				return Optional.ofNullable(raboService.processAndValidateCustomerFiles())
						.map(customerInfo -> new ResponseEntity<>(customerInfo, HttpStatus.OK))
						.orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
			}
		} catch (Exception e) {
			System.out.println("Exception in Processing File::" + e.toString());
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

}
