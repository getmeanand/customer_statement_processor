package com.rabo.filevalidator.operations;

import java.util.EnumMap;
import java.util.Map;

import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.files.CSVFile;
import com.rabo.filevalidator.files.XMLFile;

public abstract class BaseFileOperations {
	protected Map<FILE_TYPE, Object> fileMapObject;

	public BaseFileOperations() {
		super();
		initiateFileTypeMap();
	}

	private void initiateFileTypeMap() {
		fileMapObject = new EnumMap<FILE_TYPE, Object>(FILE_TYPE.class);

		fileMapObject.put(FILE_TYPE.XML, new XMLFile());
		fileMapObject.put(FILE_TYPE.CSV, new CSVFile());

	}

	public abstract Object getFileInstance(FILE_TYPE fileType);
}
