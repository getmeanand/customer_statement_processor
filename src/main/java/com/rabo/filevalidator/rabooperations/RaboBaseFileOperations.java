package com.rabo.filevalidator.rabooperations;

import java.util.EnumMap;
import java.util.Map;

import com.rabo.filevalidator.raboenums.FILE_TYPE;
import com.rabo.filevalidator.rabofiles.RaboCSVFile;
import com.rabo.filevalidator.rabofiles.RaboXMLFile;

/**
 * This Abstract class for creating the xml, or csv file instances
 * 
 * @author Anandha
 *
 */
public abstract class RaboBaseFileOperations {
	protected Map<FILE_TYPE, Object> fileMapObject;

	public RaboBaseFileOperations() {
		super();
		initiateFileTypeMap();
	}

	/**
	 * Creating file instances based on the input type and saving it in the map
	 * object
	 */
	private void initiateFileTypeMap() {
		fileMapObject = new EnumMap<FILE_TYPE, Object>(FILE_TYPE.class);

		fileMapObject.put(FILE_TYPE.XML, new RaboXMLFile());
		fileMapObject.put(FILE_TYPE.CSV, new RaboCSVFile());

	}

	public abstract Object getFileInstance(FILE_TYPE fileType);
}
