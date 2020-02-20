package com.rabo.filevalidator.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.service.RaboService;

@RestController
public class RaboController {

	@Autowired
	RaboService raboService;


	@GetMapping("/processCustomerFiles")
	public ResponseEntity<List<Record>> processAndValidateCustomerFiles()
			throws IOException, CustomerFileNotFoundException {

	
		return  Optional.ofNullable(raboService.processAndValidateCustomerFiles())
				  .map(xmlCustomerInfo -> new ResponseEntity<>(xmlCustomerInfo, HttpStatus.OK))
				  .orElse(new ResponseEntity<>(HttpStatus.CONFLICT)); 
		}

	

	

}
