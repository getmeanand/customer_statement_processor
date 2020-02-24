package com.rabo.filevalidator.rabodto;

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
public class RaboCustomerRecords {
	
	@XmlElement(name="record")
	private List<RaboCustomerAccounts> record = null;

	public List<RaboCustomerAccounts> getRecord() {
		return record;
	}


}
