package com.rabo.filevalidator.validatorfiles;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.dto.CustomerRecords;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperations;

/**
 * @author 454424
 *
 */
@Component
public class XMLFile extends FileOperations {
	private static final Logger logger = LoggerFactory.getLogger(XMLFile.class);
	File xmlCustomerFile;
	private List<CustomerAccounts> customerFileDataList = null;


	/**
	 * This readCustomerValidatorFile() reads the customer statement files from the
	 * path and  it will return the list of customer records
	 * 
	 * @param csvFile
	 * @return
	 */
	@Override
	public List<CustomerAccounts> readCustomerValidatorFile(File xmlFile) throws CustomerFileNotFoundException {
		XMLParser xmlParser = new XMLParser();
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(CustomerRecords.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			CustomerRecords record = (CustomerRecords) unMarshaller.unmarshal(xmlFile);
			customerFileDataList = record.getRecord();
			if (customerFileDataList != null && customerFileDataList.size() != FileValidatorConstants.INT_VAL_ZERO) {
				customerFileDataList = xmlParser.validateCustomerDataList(customerFileDataList);
			}
		} catch (Exception ex) {
			logger.error("Error Reading Customer File :: " + ex.getMessage());
			throw new CustomerFileNotFoundException();

		}
		return customerFileDataList;
	}

}
