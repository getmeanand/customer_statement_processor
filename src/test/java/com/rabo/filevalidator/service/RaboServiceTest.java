
package com.rabo.filevalidator.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.CustomerFileSaveException;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperationsFactory;
import com.rabo.filevalidator.service.FileValidatorService;
import com.rabo.filevalidator.utils.FileValidatorUtils;
import com.rabo.filevalidator.validatorfiles.CSVFile;

@RunWith(SpringRunner.class)
public class RaboServiceTest {

	@MockBean
	private FileOperationsFactory fileFactory;

	@MockBean
	FileValidatorUtils raboUtils;

	@InjectMocks
	private FileValidatorService raboServiceTest;

	@Before
	public void setUp() {
		raboUtils.fileStorageLocation = Paths.get(loadFilesForValidate("records.csv").getAbsolutePath())
				.toAbsolutePath().normalize();
		raboUtils.fileProcessedStorageLocation = Paths.get(loadFilesForValidate("").getAbsolutePath()).toAbsolutePath()
				.normalize();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoadAndProcessCSVFile() throws CustomerFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.CSV)).thenReturn(new CSVFile());

		List<CustomerAccounts> actualRecords = raboServiceTest.processAndValidateCustomerFiles();

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.CSV);
	}

	@Test(expected = CustomerFileSaveException.class)
	public void testStoreCustomerFiles() throws CustomerFileNotFoundException, IOException {

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.csv");
		MultipartFile mockMultipartFile = new MockMultipartFile("files", "records.csv", "multipart/form-data", is);

		raboServiceTest.storeCustomerFiles(mockMultipartFile);

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
