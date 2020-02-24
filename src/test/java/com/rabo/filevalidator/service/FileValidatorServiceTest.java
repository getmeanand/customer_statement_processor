
package com.rabo.filevalidator.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperationsFactory;
import com.rabo.filevalidator.utils.FileValidatorUtils;
import com.rabo.filevalidator.validatorfiles.CSVFile;
import com.rabo.filevalidator.validatorfiles.XMLFile;
import com.rabo.filevalidator.validatorfiles.XMLParser;

@RunWith(SpringRunner.class)
public class FileValidatorServiceTest {
	
	@MockBean
	XMLParser xmlParser;

	@MockBean
	private FileOperationsFactory fileFactory;

	@MockBean
	FileValidatorUtils raboUtils;

	@InjectMocks
	private FileValidatorService raboServiceTest;
	

	@Before
	public void setUp() {
		CustomerAccounts accounts = new CustomerAccounts();
		accounts.setAccountNumber("324ersdr");
		
		xmlParser = new XMLParser();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoadAndProcessCSVFile() throws CustomerFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.CSV)).thenReturn(new CSVFile());
		
		
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.csv");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.csv", "multipart/form-data", is);

		List<CustomerAccounts> actualRecords = raboServiceTest.processCustomerFiles(mockMultipartFile);

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.CSV);
	}

	@Test
	public void testLoadAndProcessXMLFile() throws CustomerFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new XMLFile());

		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.xml");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);

		List<CustomerAccounts> actualRecords = raboServiceTest.processCustomerFiles(mockMultipartFile);

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.XML);
	}

	@Test(expected = CustomerFileNotFoundException.class)
	public void testExpFile() throws CustomerFileNotFoundException, IOException {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.txt");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.txt", "multipart/form-data", is);

		raboServiceTest.processCustomerFiles(mockMultipartFile);

	}

}
