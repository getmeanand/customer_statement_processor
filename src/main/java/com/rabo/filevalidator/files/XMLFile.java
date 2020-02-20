package com.rabo.filevalidator.files;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.dto.Records;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperations;

@Component
public class XMLFile extends FileOperations {
	private static final Logger logger = LoggerFactory.getLogger(XMLFile.class);
	File xmlCustomerFile;
	private List<Record> customerFileDataList = null;

	@Override
	public List<Record> readCustomerValidatorFile(File xmlFile) throws CustomerFileNotFoundException {
		XMLParser xmlParser = new XMLParser();
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			Records record = (Records) unMarshaller.unmarshal(xmlFile);
			customerFileDataList = record.getRecord();
			if (customerFileDataList != null && customerFileDataList.size() != RaboConstants.INT_VAL_ZERO) {
				customerFileDataList = xmlParser.validateCustomerDataList(customerFileDataList);
			}
		} catch (Exception ex) {
			logger.error("Error Reading Customer File :: " + ex.getMessage());
			throw new CustomerFileNotFoundException();

		}
		return customerFileDataList;
	}

}
