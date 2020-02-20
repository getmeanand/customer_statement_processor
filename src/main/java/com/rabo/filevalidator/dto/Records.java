package com.rabo.filevalidator.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="records")
@XmlAccessorType(XmlAccessType.FIELD)
public class Records {
	
	@XmlElement(name="record")
	private List<Record> record = null;

	public List<Record> getRecord() {
		return record;
	}


}
