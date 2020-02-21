
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

import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.exceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.files.RaboXMLFile;
import com.rabo.filevalidator.operations.RaboFileOperationsFactory;
import com.rabo.filevalidator.utils.RaboUtils;

@RunWith(SpringRunner.class)
public class RaboXMLServiceTest {

	@MockBean
	private RaboFileOperationsFactory fileFactory;

	@MockBean
	RaboUtils raboUtils;

	@InjectMocks
	private RaboService raboServiceTest;

	@Before
	public void setUp() {
		raboUtils.fileStorageLocation = Paths.get(loadFilesForValidate("records.xml").getAbsolutePath())
				.toAbsolutePath().normalize();
		raboUtils.fileProcessedStorageLocation = Paths.get(loadFilesForValidate("").getAbsolutePath()).toAbsolutePath()
				.normalize();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProcessXMLFile() throws RaboFileNotFoundException, IOException {

		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new RaboXMLFile());

		List<RaboCustomerAccounts> actualRecords = raboServiceTest.processAndValidateCustomerFiles();

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.XML);
	}

	@Test
	public void testLoadAndProcessXMLFile() throws RaboFileNotFoundException {
		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new RaboXMLFile());
		List<RaboCustomerAccounts> actualRecords = raboServiceTest
				.loadAndProcessXMLFile(loadFilesForValidate("records.xml"));

		Assert.assertNotNull(actualRecords);

		verify(fileFactory, times(1)).getFileInstance(FILE_TYPE.XML);
	}

	@Test(expected = RaboFileNotFoundException.class)
	public void testLoadAndProcessException() throws RaboFileNotFoundException {
		when(fileFactory.getFileInstance(FILE_TYPE.XML)).thenReturn(new RaboXMLFile());
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
