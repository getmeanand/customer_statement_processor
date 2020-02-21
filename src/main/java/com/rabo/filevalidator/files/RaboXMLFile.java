package com.rabo.filevalidator.files;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabo.filevalidator.constants.RaboConstants;
import com.rabo.filevalidator.dto.RaboCustomerAccounts;
import com.rabo.filevalidator.dto.RaboCustomerRecords;
import com.rabo.filevalidator.exceptions.RaboFileNotFoundException;
import com.rabo.filevalidator.operations.RaboFileOperations;

/**
 * @author 454424
 *
 */
@Component
public class RaboXMLFile extends RaboFileOperations {
	private static final Logger logger = LoggerFactory.getLogger(RaboXMLFile.class);
	File xmlCustomerFile;
	private List<RaboCustomerAccounts> customerFileDataList = null;


	/**
	 * This readCustomerValidatorFile() reads the customer statement files from the
	 * path and  it will return the list of customer records
	 * 
	 * @param csvFile
	 * @return
	 */
	@Override
	public List<RaboCustomerAccounts> readCustomerValidatorFile(File xmlFile) throws RaboFileNotFoundException {
		RaboXMLParser xmlParser = new RaboXMLParser();
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(RaboCustomerRecords.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			RaboCustomerRecords record = (RaboCustomerRecords) unMarshaller.unmarshal(xmlFile);
			customerFileDataList = record.getRecord();
			if (customerFileDataList != null && customerFileDataList.size() != RaboConstants.INT_VAL_ZERO) {
				customerFileDataList = xmlParser.validateCustomerDataList(customerFileDataList);
			}
		} catch (Exception ex) {
			logger.error("Error Reading Customer File :: " + ex.getMessage());
			throw new RaboFileNotFoundException();

		}
		return customerFileDataList;
	}

}
