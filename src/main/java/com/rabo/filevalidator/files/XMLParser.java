package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.dto.Record;
import com.rabo.filevalidator.utils.RaboUtils;

public class XMLParser extends FileParser {

	@Override
	public List<Record> validateCustomerDataList(List<Record> customerFileList) {
		return RaboUtils.validateCustomerRecords(customerFileList);
	}

	@Override
	public Record parseCustomerInformation(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}
