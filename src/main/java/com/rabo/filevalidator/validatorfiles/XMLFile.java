package com.rabo.filevalidator.validatorfiles;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rabo.filevalidator.constants.FileValidatorConstants;
import com.rabo.filevalidator.dto.CustomerAccounts;
import com.rabo.filevalidator.exceptions.CustomerFileNotFoundException;
import com.rabo.filevalidator.operations.FileOperations;

/**
 * @author 454424
 *
 */
@Component
public class XMLFile extends FileOperations {
	private static final Logger logger = LoggerFactory.getLogger(XMLFile.class);

	private XMLParser xmlParser;

	/**
	 * This readCustomerValidatorFile() reads the customer statement files from the
	 * path and it will return the list of customer records
	 * 
	 * @param xmlFile
	 * @return
	 */
	@Override
	public List<CustomerAccounts> readCustomerValidatorFile(MultipartFile xmlFile) throws IOException {
		List<CustomerAccounts> customerFileDataList = null;
		xmlParser = new XMLParser();
		try {

			String inputXML = new String(xmlFile.getInputStream().readAllBytes());
			customerFileDataList = new XmlMapper().readValue(inputXML, new TypeReference<List<CustomerAccounts>>() {
			});

			if (customerFileDataList != null && customerFileDataList.size() != FileValidatorConstants.INT_VAL_ZERO) {
				customerFileDataList = xmlParser.validateCustomerDataList(customerFileDataList);
			}
		} catch (IOException ex) {
			logger.error("Error Reading XML Customer File :: " + ex.getMessage());
			throw new CustomerFileNotFoundException();

		}
		return customerFileDataList;
	}

}
