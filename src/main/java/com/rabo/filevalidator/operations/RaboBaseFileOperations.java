package com.rabo.filevalidator.operations;

import java.util.EnumMap;
import java.util.Map;

import com.rabo.filevalidator.enums.FILE_TYPE;
import com.rabo.filevalidator.files.RaboCSVFile;
import com.rabo.filevalidator.files.RaboXMLFile;

public abstract class RaboBaseFileOperations {
	protected Map<FILE_TYPE, Object> fileMapObject;

	public RaboBaseFileOperations() {
		super();
		initiateFileTypeMap();
	}

	private void initiateFileTypeMap() {
		fileMapObject = new EnumMap<FILE_TYPE, Object>(FILE_TYPE.class);

		fileMapObject.put(FILE_TYPE.XML, new RaboXMLFile());
		fileMapObject.put(FILE_TYPE.CSV, new RaboCSVFile());

	}

	public abstract Object getFileInstance(FILE_TYPE fileType);
}
