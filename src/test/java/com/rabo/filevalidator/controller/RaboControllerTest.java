package com.rabo.filevalidator.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.service.RaboService;

@WebMvcTest(RaboController.class)
@RunWith(SpringRunner.class)
public class RaboControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RaboService raboService;
	

	List<Record> records;

	@Before
	public void setUp() {
		records = new ArrayList();
		Record record = new Record();
		record.setStartBalance(100);
		record.setMutation(50);
		record.setEndBalance(50);
		records.add(record);
		

	}

	@Test
	public void testWithCSVFileContent() throws Exception {
		when(raboService.processAndValidateCustomerFiles()).thenReturn(records);
		
		ResultActions resultActions = mockMvc.perform(get("/processCustomerFiles").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		Assert.assertNotNull(contentAsString);
	}

	@Test
	public void testWithXMLFileContent() throws Exception {
		when(raboService.processAndValidateCustomerFiles()).thenReturn(null);
		ResultActions resultActions = mockMvc.perform(get("/processCustomerFiles").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict()).andDo(print());
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		Assert.assertNotNull(StringUtils.isEmpty(contentAsString));
	}
	
	

}