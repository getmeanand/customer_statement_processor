
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

import com.rabo.filevalidator.rabodto.RaboCustomerAccounts;
import com.rabo.filevalidator.raboenums.FILE_TYPE;
import com.rabo.filevalidator.raboexceptions.RaboCustomerFileSaveException;
import com.rabo.filevalidator.raboexceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.rabofiles.RaboCSVFile;
import com.rabo.filevalidator.rabooperations.RaboFileOperationsFactory;
import com.rabo.filevalidator.raboservice.RaboService;
import com.rabo.filevalidator.raboutils.RaboUtils;

@RunWith(SpringRunner.class)
public class RaboServiceTest {

	@MockBean
	private RaboFileOperationsFactory fileFactory;

	@MockBean
	RaboUtils raboUtils;

	@InjectMocks
	private RaboService raboServiceTest;

	@Before
	public void setUp() {
		raboUtils.fileStorageLocation = Paths.get(loadFilesForValidate("records.csv").getAbsolutePath())
				.toAbsolutePath().normalize();
		raboUtils.fileProcessedStorageLocation = Paths.get(loadFilesForValidate("").getAbsolutePath()).toAbsolutePath()
				.normalize();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testLoadAndProcessCSVFile() throws RaboFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.CSV)).thenReturn(new RaboCSVFile());

		List<RaboCustomerAccounts> actualRecords = raboServiceTest.processAndValidateCustomerFiles();

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.CSV);
	}

	@Test(expected = RaboCustomerFileSaveException.class)
	public void testStoreCustomerFiles() throws RaboFileNotFoundException, IOException {

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
