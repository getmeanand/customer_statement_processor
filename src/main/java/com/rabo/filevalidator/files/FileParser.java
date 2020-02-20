package com.rabo.filevalidator.files;

import java.util.List;

import com.rabo.filevalidator.dto.Record;

public abstract class FileParser {
	public abstract Record parseCustomerInformation(String data);

	public abstract List<Record> validateCustomerDataList(List<Record> customerFileList);

}
