
package com.rabo.filevalidator.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperationsFactory;
import com.rabo.filevalidator.service.FileValidatorService;
import com.rabo.filevalidator.utils.FileValidatorUtils;
import com.rabo.filevalidator.validatorfiles.XMLFile;

@RunWith(SpringRunner.class)
public class RaboXMLServiceTest {

	@MockBean
	private FileOperationsFactory fileFactory;

	@MockBean
	FileValidatorUtils raboUtils;

	@InjectMocks
	private FileValidatorService raboServiceTest;

	@Before
	public void setUp() {
		raboUtils.fileStorageLocation = Paths.get(loadFilesForValidate("records.xml").getAbsolutePath())
				.toAbsolutePath().normalize();
		raboUtils.fileProcessedStorageLocation = Paths.get(loadFilesForValidate("").getAbsolutePath()).toAbsolutePath()
				.normalize();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProcessXMLFile() throws CustomerFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new XMLFile());

		List<CustomerAccounts> actualRecords = raboServiceTest.processAndValidateCustomerFiles();

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.XML);
	}

	@Test
	public void testLoadAndProcessXMLFile() throws CustomerFileNotFoundException {
		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new XMLFile());
		List<CustomerAccounts> actualRecords = raboServiceTest
				.loadAndProcessXMLFile(loadFilesForValidate("records.xml"));

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.XML);
	}

	@Test(expected = CustomerFileNotFoundException.class)
	public void testLoadAndProcessException() throws CustomerFileNotFoundException {
		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new XMLFile());
		raboServiceTest.loadAndProcessXMLFile(loadFilesForValidate("records1.xml"));
	}

	public File loadFilesForValidate(String paramFileName) {
		File loadFile = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			loadFile = new File(classLoader.getResource(paramFileName).getFile());
		} catch (Exception ex) {
		}
		return loadFile;
	}

}
