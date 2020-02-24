package com.rabo.filevalidator.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Anandha
 *
 */
@XmlRootElement(name="records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRecords {
	
	@XmlElement(name="record")
	private List<CustomerAccounts> record = null;

	public List<CustomerAccounts> getRecord() {
		return record;
	}


}
