package com.rabo.filevalidator.controller;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.rabo.filevalidator.controller.FileValidatorController;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.service.FileProperties;
import com.rabo.filevalidator.service.FileValidatorService;

@WebMvcTest(FileValidatorController.class)
@RunWith(SpringRunner.class)
public class RaboControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FileValidatorService raboService;

	Path fileStorageLocation;

	List<CustomerAccounts> records;
	String strCustomerFile = new String("records.xml");

	@MockBean
	FileProperties raboFileProperties;

	@Before
	public void setUp() {
		records = new ArrayList();
		CustomerAccounts record = new CustomerAccounts();
		record.setStartBalance(100);
		record.setMutation(50);
		record.setEndBalance(50);
		records.add(record);

	}

	@Test
	public void testWithCSVFileContent() throws Exception {
		when(raboService.processAndValidateCustomerFiles()).thenReturn(records);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.csv");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "records.csv", "multipart/form-data", is);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.fileUpload("/uploadCustomerFiles").file(mockMultipartFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		Assert.assertNotNull(contentAsString);
	}

	@Test
	public void testWithXMLFileContent() throws Exception {
		when(raboService.processAndValidateCustomerFiles()).thenReturn(records);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.xml");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "multipart/form-data", is);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.fileUpload("/uploadCustomerFiles").file(mockMultipartFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

		String contentAsString = result.getResponse().getContentAsString();

		Assert.assertNotNull(contentAsString);
	}

	@Test
	public void testStoreCustomerFiles() throws Exception {
		when(raboService.processAndValidateCustomerFiles()).thenReturn(records);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.csv");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "records.csv", "multipart/form-data", is);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.fileUpload("/uploadCustomerFiles").file(mockMultipartFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

	}
	
	@Test
	public void testStoreCustomerFilesService() throws Exception {
	
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("records.csv");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("files", "records.csv", "multipart/form-data", is);

		when(raboService.storeCustomerFiles(Mockito.any())).thenReturn(strCustomerFile);
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.fileUpload("/uploadCustomerFiles").file(mockMultipartFile)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();

	}

}