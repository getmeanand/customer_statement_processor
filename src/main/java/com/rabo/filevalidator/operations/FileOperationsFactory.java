package com.rabo.filevalidator.operations;

import org.springframework.stereotype.Component;

import com.rabo.filevalidator.enums.FILE_TYPE;

/**
 * @author Anandha
 *
 */
@Component
public class FileOperationsFactory extends BaseFileOperations {
	/**
	 * this function accepts the customer input file types ie xml or csv and return
	 * the corresponding instances accordingly from the fileMapObject. this is the
	 * implementation of factory pattern method
	 * 
	 * @param strFileFile
	 * 
	 */
	public Object getFileInstance(FILE_TYPE fileTypeObj) {
		if (fileTypeObj == null) {
			return null;
		}
		if (FILE_TYPE.XML.equals(fileTypeObj)) { // checks if file type is xml
			return fileMapObject.get(FILE_TYPE.XML);
		} else if (FILE_TYPE.CSV.equals(fileTypeObj)) {// checks if file type is csv
			return fileMapObject.get(FILE_TYPE.CSV);
		}

		return null;
	}

}
